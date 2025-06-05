package com.example.vibeaway.data.repository

import android.content.Context
import android.util.Log
import com.example.vibeaway.data.activity.datasource.ActivitiesDataSource
import com.example.vibeaway.data.activity.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.activity.model.Activity
import com.example.vibeaway.data.activity.model.ActivityInfo
import com.example.vibeaway.data.activity.model.CompatibleActivity
import com.example.vibeaway.data.activity.model.CompatibleActivityCategory
import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.location.model.Location
import com.example.vibeaway.data.locationdetails.model.LocationDetails
import com.example.vibeaway.data.network.model.Content
import com.example.vibeaway.data.network.model.GoogleAiRequestDTO
import com.example.vibeaway.data.network.model.GoogleAiResponseDTO
import com.example.vibeaway.data.network.model.Part
import com.example.vibeaway.data.network.resource.processApiResource
import com.example.vibeaway.data.network.service.GoogleAiApi
import com.example.vibeaway.data.quiz.model.BFIDimension
import com.example.vibeaway.data.quiz.model.BFIResult
import com.example.vibeaway.data.recommendation.model.MatchedActivityDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import kotlin.String
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.firstOrNull
import kotlin.text.indexOf
import kotlin.text.substring

/**
 * Repository responsible with computing a set of compatible [Location] with a BFI dataset, using
 * multiple datasets that include [ActivityInfo], [Activity] etc.
 */
class RecommendationRepositoryImpl(
    private val googleAiApiKey: String,
    private val activityCategoriesDataSource: ActivityCategoriesDataSource,
    private val activitiesDataSource: ActivitiesDataSource,
    private val locationsDataSource: LocationsDataSource,
    private val activityDetailsDataSource: ActivityDetailsDataSource,
    private val databaseRepository: DatabaseRepository,
    private val googleAiApi: GoogleAiApi,
    private val context: Context,
) : RecommendationRepository, JsonDataSource() {

    private val recommendations = ConcurrentHashMap<LocationDetails, List<ActivityDetails>>()

    override suspend fun getRecommendedLocationsDetails(): List<LocationDetails> {
        val cachedRecommendations = when {
            this.recommendations.isEmpty() -> getRecommendedActivitiesDetailsFromFile()
            else -> this.recommendations
        }

        val recommendations = when {
            cachedRecommendations.isEmpty() -> getRecommendedActivitiesDetailsFromAiAgent()
            else -> cachedRecommendations
        }

        return recommendations.toList().sortedBy { (_, activitiesDetails) ->
            val totalScore = activitiesDetails.mapNotNull { it.score }.sum()
            val totalRating = activitiesDetails.sumOf { it.rating }
            activitiesDetails.size * totalScore * totalRating
        }.toMap().keys.toList()
    }

    override suspend fun getRecommendedActivitiesDetails(): List<ActivityDetails> {
        val cachedRecommendations = when {
            this.recommendations.isEmpty() -> getRecommendedActivitiesDetailsFromFile()
            else -> this.recommendations
        }

        val recommendations = when {
            cachedRecommendations.isEmpty() -> getRecommendedActivitiesDetailsFromAiAgent()
            else -> cachedRecommendations
        }

        val sortedRecommendations = recommendations.toList().sortedByDescending { (_, activitiesDetails) ->
            val totalScore = activitiesDetails.mapNotNull { it.score }.sum()
            val totalRating = activitiesDetails.sumOf { it.rating }
            activitiesDetails.size * totalScore * totalRating
        }.toMap()

        sortedRecommendations.entries.forEach { (locationDetails, activities) ->
            Log.d(TAG, "LocationDetails: ${locationDetails.city}")
            activities.forEach { Log.d(TAG, "Activity: ${it.title}, ${it.rating}, ${it.score}") }
        }

        return sortedRecommendations.values.flatten()
    }

    /**
     * Returns a list of [CompatibleActivityCategory] by computing some correlations between
     * a dataset of [BFIResult] and a dataset of [ActivityInfo]
     */
    private suspend fun getCompatibleActivityCategories(): List<CompatibleActivityCategory> {
        val activityCategories = activityCategoriesDataSource.getActivityCategories()
        val bfiResults = databaseRepository.getBFIScores()?.mapNotNull { (dimensionId, score) ->
            BFIResult(
                score = score,
                bfiDimension = BFIDimension.getBFIDimensionById(
                    id = dimensionId
                ) ?: return@mapNotNull null
            )
        } ?: emptyList()

        return activityCategories.map { activityCategory ->
            val scores = activityCategory.bfiDimensionsInfo.mapNotNull { bfiDimensionInfo ->
                val bfiDimensionResult = bfiResults.firstOrNull { bFIResult ->
                    bFIResult.bfiDimension.id == bfiDimensionInfo.id
                } ?: return@mapNotNull null

                bfiDimensionResult.score * bfiDimensionInfo.regressionsWeight
            }

            CompatibleActivityCategory(
                id = activityCategory.id,
                name = activityCategory.name,
                score = scores.sum()
            )
        }
    }

    /**
     * Returns a list of [CompatibleActivity] by computing some correlations between
     * a dataset of [CompatibleActivityCategory] and a dataset of [Activity]
     */
    private fun getCompatibleActivities(
        compatibleActivityCategories: List<CompatibleActivityCategory>
    ): List<CompatibleActivity> {
        val activities = activitiesDataSource.getActivities()

        return activities.mapNotNull { activity ->
            val computedCategory = compatibleActivityCategories.firstOrNull { computedCategory ->
                computedCategory.id == activity.categoryId
            } ?: return@mapNotNull null

            val score = activity.estimatedCorrelation * computedCategory.score

            CompatibleActivity(
                id = activity.id,
                description = activity.description,
                score = score
            )
        }
    }

    suspend fun getRecommendedActivitiesDetailsFromAiAgent(): Map<LocationDetails, List<ActivityDetails>> {
        val allActivitiesDetails = activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        )

        val mappedActivitiesDetails = allActivitiesDetails.mapValues { (_, activitiesDetails) ->
            activitiesDetails.map {
                "id: ${it.id}, name: ${it.title}, types: ${it.types}"
            }
        }

        val compatibleCategories = getCompatibleActivityCategories()
        val compatibleActivities = getCompatibleActivities(compatibleCategories).map {
            "description: ${it.description}, score: ${it.score}"
        }

        val asyncCalls = withContext(Dispatchers.Default) {
            return@withContext mappedActivitiesDetails.mapValues { (_, mappedActivitiesDetails) ->
                val prompt = """
            Match each activity from $mappedActivitiesDetails using the name and types with the 
            most related compatible activities from $compatibleActivities using description. 
            From the matched ones choose only the one with the highest score. To each 
            matching activity id from the first list associate the highest score from the matched 
            ones from the second list and give me just a raw JSON list response where the JSON 
            object keys are id and score as string not as double. No null value are to be added. 
            Activities with no match can be left out.
                """.trimIndent()

                val request = GoogleAiRequestDTO(
                    contents = listOf(Content(role = "user", parts = listOf(Part(text = prompt))))
                )

                async {
                    processApiResource {
                        googleAiApi.generateContent(
                            request = request,
                            apiKey = googleAiApiKey
                        )
                    }
                }
            }
        }

        val resources = asyncCalls.mapValues { (_, asyncCall) -> asyncCall.await() }

        val matchedActivitiesDetails = resources.mapValues { (locationId, resource) ->
            val matchedActivitiesDetails = extractListOfFoundActivities(resource.payload)
            matchedActivitiesDetails.mapNotNull { matchedActivityDTO ->
                val activitiesBundle = allActivitiesDetails[locationId]
                val activityDetails = activitiesBundle?.firstOrNull {
                    it.id == matchedActivityDTO.id
                } ?: return@mapNotNull null

                try {
                    val score = matchedActivityDTO.score?.toDoubleOrNull() ?: return@mapNotNull null
                    activityDetails.copy(score = score)
                } catch (_: Exception) {
                    return@mapNotNull null
                }
            }
        }

        val locationDetails = getLocationsDetails()
        val recommendations = matchedActivitiesDetails.mapKeys { (locationId, _) ->
            locationDetails.first { it.id == locationId }
        }

        this.recommendations.clear()
        recommendations.forEach { (locationDetails, activitiesDetails) ->
            this.recommendations[locationDetails] = activitiesDetails
        }

        cacheRecommendations(recommendations)
        return recommendations
    }

    private suspend fun getLocationsDetails(): List<LocationDetails> {
        val locations = locationsDataSource.getLocations()
        val locationsWithActivitiesDetails = activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        )

        val locationDetails = locations.map { location ->
            LocationDetails(
                id = location.id,
                city = location.city,
                country = location.country,
                description = location.description,
                latitude = location.latitude,
                longitude = location.longitude,
                imageFileName = location.imageFileName,
                activitiesDetails = locationsWithActivitiesDetails[location.id] ?: emptyList()
            )
        }

        return locationDetails
    }

    private fun extractListOfFoundActivities(
        response: GoogleAiResponseDTO?
    ): List<MatchedActivityDTO> {
        try {
            val reply = response?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
            Log.d(TAG, "reply: $reply")
            val startIndex = reply?.indexOf("[") ?: return emptyList()
            val endIndex = reply.indexOf("]") + 1
            val jsonStringArray = reply.substring(startIndex, endIndex)

            val json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }

            val matchedActivities = json.decodeFromString<List<MatchedActivityDTO>>(jsonStringArray)
            return matchedActivities
        } catch (_: Exception) {
            return emptyList()
        }
    }

    private suspend fun getRecommendedActivitiesDetailsFromFile(): Map<LocationDetails, List<ActivityDetails>> {
        val locationDetails = getLocationsDetails()
        val allActivitiesDetails = activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        )

        val jsonString = getJson(context, FILE_PATH) ?: return emptyMap()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val matchedActivitiesDTOs = json.decodeFromString<List<MatchedActivityDTO>>(jsonString)
        val allMatchedActivityDetails = allActivitiesDetails.mapValues { (_, activitiesDetails) ->
            activitiesDetails.mapNotNull { activityDetails ->
                try {
                    val matchedActivityDTO = matchedActivitiesDTOs.firstOrNull {
                        it.id == activityDetails.id
                    }

                    val score = matchedActivityDTO?.score?.toDoubleOrNull() ?: return@mapNotNull null
                    activityDetails.copy(score = score)
                } catch (_: Exception) {
                    return@mapNotNull null
                }
            }
        }

        val recommendations = allMatchedActivityDetails.mapKeys { (locationId, _) ->
            locationDetails.first { it.id == locationId }
        }

        this.recommendations.clear()
        recommendations.forEach { (locationDetails, activitiesDetails) ->
            this.recommendations[locationDetails] = activitiesDetails
        }

        return recommendations
    }

    private fun cacheRecommendations(recommendations: Map<LocationDetails, List<ActivityDetails>>) {
        val jsonObj = recommendations.values.map { activities ->
            activities.map {
                MatchedActivityDTO(
                    id = it.id,
                    score = it.score.toString()
                )
            }
        }.flatten()

        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val jsonString = json.encodeToString(jsonObj)
        writeJson(context, FILE_PATH, jsonString)
    }

    companion object {
        private const val TAG = "RecommendationRepository"
        private const val FILE_PATH = "matched_activities.json"
    }
}
