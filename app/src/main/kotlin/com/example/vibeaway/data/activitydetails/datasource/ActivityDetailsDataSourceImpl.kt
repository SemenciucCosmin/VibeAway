package com.example.vibeaway.data.activitydetails.datasource

import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.activitydetails.model.ActivityDetailsFileDTO
import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.network.model.ActivityDetailsDTO
import com.example.vibeaway.data.network.resource.processApiResource
import com.example.vibeaway.data.network.service.ActivitiesDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.isNotEmpty
import kotlin.collections.mapNotNull

/**
 * Data source for providing the list of [ActivityDetails]
 */
class ActivityDetailsDataSourceImpl(
    private val activityDetailsApi: ActivitiesDetailsApi,
    private val locationsDataSource: LocationsDataSource,
) : ActivityDetailsDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [ActivityDetails]
     */
    private val activitiesDetails = ConcurrentHashMap<String, List<ActivityDetails>>()

    override suspend fun getActivitiesDetails(forceApi: Boolean): Map<String, List<ActivityDetails>> {
        return when {
            this.activitiesDetails.isNotEmpty() -> this.activitiesDetails

            forceApi -> {
                val activitiesDetailsFromApi = getActivitiesDetailsFromApi()

                this.activitiesDetails.clear()

                activitiesDetailsFromApi.forEach { (locationId, activitiesDetails) ->
                    this.activitiesDetails[locationId] = activitiesDetails
                }

                activitiesDetailsFromApi
            }

            else -> {
                val activitiesDetailsFromFile = getActivitiesDetailsFromFile()

                this.activitiesDetails.clear()

                activitiesDetailsFromFile.forEach { (locationId, activitiesDetails) ->
                    this.activitiesDetails[locationId] = activitiesDetails
                }

                activitiesDetailsFromFile
            }
        }
    }

    private suspend fun getActivitiesDetailsFromApi(): Map<String, List<ActivityDetails>> {
        return withContext(Dispatchers.Default) {
            val locations = locationsDataSource.getLocations()
            val activitiesDetailsAsync = locations.associateWith { location ->
                async {
                    processApiResource {
                        activityDetailsApi.getActivitiesDetails(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            radius = DEFAULT_RADIUS_KM
                        )
                    }
                }
            }

            val resources = activitiesDetailsAsync.mapValues { (_, activityDetailsAsync) ->
                activityDetailsAsync.await()
            }

            val activitiesDetails = resources.mapValues { (_, resource) ->
                val activitiesDetailsDTOs = resource.payload?.data ?: emptyList()
                mapActivitiesDetailsDTOtoActivitiesDetails(activitiesDetailsDTOs)
            }

            val locationsWithActivitiesDetails = activitiesDetails.mapKeys { (location, _) ->
                location.id
            }

            return@withContext locationsWithActivitiesDetails
        }
    }

    /**
     * Parses a json file into a list of [ActivityDetailsDTO] and maps
     * it to a list of [ActivityDetails].
     */
    private fun getActivitiesDetailsFromFile(): Map<String, List<ActivityDetails>> {
        val jsonString = getJson(FILE_PATH)
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val activitiesDetailsDTOs = json.decodeFromString<List<ActivityDetailsFileDTO>>(jsonString)

        val locationsIds = activitiesDetailsDTOs.mapNotNull { it.locationId }
        val groupedActivitiesDetailsFileDTOs = activitiesDetailsDTOs.groupBy { it.locationId }

        val locationsWithActivitiesDetails = locationsIds.associateWith {
            groupedActivitiesDetailsFileDTOs[it] ?: emptyList()
        }.mapValues { (_, activitiesDetailsFileDTO) ->
            mapActivitiesDetailsFileDTOtoActivitiesDetails(activitiesDetailsFileDTO)
        }

        return locationsWithActivitiesDetails
    }

    /**
     * Maps a list of [ActivityDetailsDTO] to a list of [ActivityDetails].
     */
    private fun mapActivitiesDetailsDTOtoActivitiesDetails(
        activitiesDetailsDTOs: List<ActivityDetailsDTO>
    ): List<ActivityDetails> {
        return activitiesDetailsDTOs.mapNotNull { activityDetailsDTO ->
            ActivityDetails(
                id = activityDetailsDTO.id ?: return@mapNotNull null,
                title = activityDetailsDTO.name ?: return@mapNotNull null,
                description = activityDetailsDTO.description,
                imageUrl = activityDetailsDTO.imagesUrls.firstOrNull(),
                latitude = activityDetailsDTO.geolocation?.latitude ?: return@mapNotNull null,
                longitude = activityDetailsDTO.geolocation.longitude ?: return@mapNotNull null,
                priceAmount = activityDetailsDTO.price?.amount,
                currencyCode = activityDetailsDTO.price?.currencyCode,
                bookingUrl = activityDetailsDTO.bookingUrl,
                rating = activityDetailsDTO.rating ?: return@mapNotNull null,
            )
        }
    }

    /**
     * Maps a list of [ActivityDetailsFileDTO] to a list of [ActivityDetails].
     */
    private fun mapActivitiesDetailsFileDTOtoActivitiesDetails(
        activitiesDetailsFileDTOs: List<ActivityDetailsFileDTO>
    ): List<ActivityDetails> {
        return activitiesDetailsFileDTOs.mapNotNull { activityDetailsFileDTO ->
            ActivityDetails(
                id = activityDetailsFileDTO.id ?: return@mapNotNull null,
                title = activityDetailsFileDTO.name ?: return@mapNotNull null,
                description = activityDetailsFileDTO.description,
                imageUrl = activityDetailsFileDTO.imageUrl,
                latitude = activityDetailsFileDTO.latitude ?: return@mapNotNull null,
                longitude = activityDetailsFileDTO.longitude ?: return@mapNotNull null,
                priceAmount = activityDetailsFileDTO.priceAmount,
                currencyCode = activityDetailsFileDTO.currencyCode,
                bookingUrl = activityDetailsFileDTO.bookingUrl,
                rating = activityDetailsFileDTO.rating ?: return@mapNotNull null,
            )
        }
    }

    companion object {
        private const val FILE_PATH = "activities_details.json"
        private const val DEFAULT_RADIUS_KM = 5
    }
}
