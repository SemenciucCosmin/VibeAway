package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.Activity
import com.example.vibeaway.data.model.ActivityDTO
import kotlinx.serialization.json.Json
import java.util.Collections

class ActivitiesDataSourceImpl : ActivitiesDataSource, JsonDataSource() {

    private val activities: MutableList<Activity> = Collections.synchronizedList(mutableListOf())

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

    private fun getActivitiesFromFile(): List<Activity> {
        val jsonString = getJson(FILE_PATH)
        val activityDTOs = Json.decodeFromString<List<ActivityDTO>>(jsonString)
        return mapActivityDTOtoActivity(activityDTOs)
    }

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
