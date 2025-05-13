package com.example.vibeaway.data.recommendation.datasource

import com.example.vibeaway.data.core.datasource.JsonDataSource
import com.example.vibeaway.data.recommendation.model.Activity
import com.example.vibeaway.data.recommendation.model.ActivityDTO
import kotlinx.serialization.json.Json
import java.util.Collections

/**
 * Data source for providing the list of [Activity] from a json file or from in memory cache.
 */
class ActivitiesDataSourceImpl : ActivitiesDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [Activity]
     */
    private val activities: MutableList<Activity> = Collections.synchronizedList(mutableListOf())

    /**
     * Parses a json file into a list of [Activity] or retrieves it from in memory cache.
     */
    override fun getActivities(): List<Activity> {
        return when {
            this.activities.isNotEmpty() -> this.activities

            else -> {
                val activitiesFromFile = getActivitiesFromFile()
                this.activities.clear()
                this.activities.addAll(activitiesFromFile)
                activitiesFromFile
            }
        }
    }

    /**
     * Parses a json file into a list of [ActivityDTO] and maps it to a list of [Activity].
     */
    private fun getActivitiesFromFile(): List<Activity> {
        val jsonString = getJson(FILE_PATH)
        val activityDTOs = Json.decodeFromString<List<ActivityDTO>>(jsonString)
        return mapActivityDTOtoActivity(activityDTOs)
    }

    /**
     * Maps a list of [ActivityDTO] to a list of [Activity].
     */
    private fun mapActivityDTOtoActivity(activityDTOs: List<ActivityDTO>): List<Activity> {
        return activityDTOs.mapNotNull { activityDTO ->
            Activity(
                id = activityDTO.id ?: return@mapNotNull null,
                description = activityDTO.description ?: return@mapNotNull null,
                categoryId = activityDTO.categoryId ?: return@mapNotNull null,
                estimatedCorrelation = activityDTO.estimatedCorrelation ?: return@mapNotNull null,
            )
        }
    }

    companion object {
        private const val FILE_PATH = "activities.json"
    }
}
