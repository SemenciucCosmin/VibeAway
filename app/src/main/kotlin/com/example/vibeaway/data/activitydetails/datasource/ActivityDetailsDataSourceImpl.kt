package com.example.vibeaway.data.activitydetails.datasource

import android.util.Log
import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.activitydetails.model.ActivityDetailsFileDTO
import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.network.model.ActivityDetailsDTO
import com.example.vibeaway.data.network.resource.processApiResource
import com.example.vibeaway.data.network.service.ActivitiesDetailsApi
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.mapNotNull

/**
 * Data source for providing the list of [ActivityDetails]
 */
class ActivityDetailsDataSourceImpl(
    private val activityDetailsApi: ActivitiesDetailsApi,
    private val locationsDataSource: LocationsDataSource,
    private val placesClient: PlacesClient,
) : ActivityDetailsDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [ActivityDetails]
     */
    private val activitiesDetails = ConcurrentHashMap<String, List<ActivityDetails>>()

    override suspend fun getActivitiesDetails(
        forceApi: Boolean,
        forceGoogle: Boolean
    ): Map<String, List<ActivityDetails>> {
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

            forceGoogle -> {
                val activitiesDetailsFromGoogle = getActivitiesDetailsFromGoogle()

                this.activitiesDetails.clear()

                activitiesDetailsFromGoogle.forEach { (locationId, activitiesDetails) ->
                    this.activitiesDetails[locationId] = activitiesDetails
                }

                activitiesDetailsFromGoogle
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

    private suspend fun getActivitiesDetailsFromGoogle(): Map<String, List<ActivityDetails>> {
        val locations = locationsDataSource.getLocations().drop(90).take(10)
        val locationsWithPlaces = locations.associateWith { location ->
            getLocationPlace(location.latitude, location.longitude)
        }

        val locationsWithActivitiesDetails = locationsWithPlaces.mapValues { (_, places) ->
            mapPlaceToActivityDetails(places)
        }

        val activitiesDetails = locationsWithActivitiesDetails.mapKeys { (location, _) ->
            location.id
        }

        return activitiesDetails
    }

    private suspend fun getLocationPlace(latitude: Double, longitude: Double): List<Place> {
        val center = LatLng(latitude, longitude)
        val circle = CircularBounds.newInstance(center, DEFAULT_RADIUS_M)
        val searchNearbyRequest = SearchNearbyRequest.builder(circle, GOOGLE_PLACE_FIELDS).apply {
            setIncludedTypes(GOOGLE_INCLUDED_TYPES)
            setMaxResultCount(DEFAULT_RESULTS_COUNT)
        }.build()

        val response = placesClient.searchNearby(searchNearbyRequest).apply {
            addOnSuccessListener { response ->
                response.places.forEach { place ->
                    Log.d(TAG, "Success: $place")
                }
            }

            addOnFailureListener { exception ->
                Log.d(TAG, "Exception: ${exception.cause}, ${exception.message}")
            }

            addOnCanceledListener {
                Log.d(TAG, "Canceled")
            }
        }.await()

        return response.places
    }

    private suspend fun getPlaceImageUrl(place: Place): String? {
        val metadata = place.photoMetadatas?.firstOrNull() ?: return null
        val photoRequest = FetchResolvedPhotoUriRequest.builder(metadata)
            .setMaxWidth(500)
            .setMaxHeight(300)
            .build()

        return placesClient.fetchResolvedPhotoUri(photoRequest).await().uri?.toString()
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
                types = emptyList()
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
                types = activityDetailsFileDTO.types
            )
        }
    }

    private suspend fun mapPlaceToActivityDetails(places: List<Place>): List<ActivityDetails> {
        return places.mapNotNull { place ->
            ActivityDetails(
                id = place.id ?: return@mapNotNull null,
                title = place.displayName ?: return@mapNotNull null,
                description = null,
                imageUrl = getPlaceImageUrl(place),
                latitude = place.location?.latitude ?: return@mapNotNull null,
                longitude = place.location?.longitude ?: return@mapNotNull null,
                priceAmount = null,
                currencyCode = null,
                bookingUrl = null,
                rating = place.rating ?: return@mapNotNull null,
                types = place.placeTypes ?: return@mapNotNull null,
            )
        }
    }

    companion object {
        private const val TAG = "ActivityDetailsDataSource"
        private const val FILE_PATH = "activities_details_google.json"
        private const val DEFAULT_RADIUS_KM = 5
        private const val DEFAULT_RADIUS_M = 5000.0
        private const val DEFAULT_RESULTS_COUNT = 20
        private val GOOGLE_INCLUDED_TYPES = listOf(
            "restaurant",
            "cafe",
            "bar",
            "museum",
            "art_gallery",
            "movie_theater",
            "zoo",
            "aquarium",
            "amusement_park",
            "tourist_attraction",
            "spa",
            "church",
            "mosque",
            "synagogue",
            "hindu_temple",
            "park",
            "campground",
            "shopping_mall",
        )

        private val GOOGLE_PLACE_FIELDS = listOf(
            Place.Field.DISPLAY_NAME,
            Place.Field.FORMATTED_ADDRESS,
            Place.Field.ID,
            Place.Field.LOCATION,
            Place.Field.PHOTO_METADATAS,
            Place.Field.RATING,
            Place.Field.TYPES,
        )
    }
}
