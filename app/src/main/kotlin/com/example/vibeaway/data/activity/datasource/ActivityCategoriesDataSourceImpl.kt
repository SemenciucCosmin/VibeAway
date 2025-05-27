package com.example.vibeaway.data.activity.datasource

import com.example.vibeaway.data.activity.model.ActivityCategory
import com.example.vibeaway.data.activity.model.ActivityCategoryDTO
import com.example.vibeaway.data.core.datasource.JsonDataSource
import kotlinx.serialization.json.Json
import java.util.Collections

/**
 * Data source for providing the list of [ActivityCategory] from a json file or from in memory cache.
 */
class ActivityCategoriesDataSourceImpl : ActivityCategoriesDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [ActivityCategory]
     */
    private val activityCategories: MutableList<ActivityCategory> =
        Collections.synchronizedList(mutableListOf())

    /**
     * Parses a json file into a list of [ActivityCategory] or retrieves it from in memory cache.
     */
    override fun getActivityCategories(): List<ActivityCategory> {
        return when {
            this.activityCategories.isNotEmpty() -> this.activityCategories

            else -> {
                val activityCategoriesFromFile = getActivityCategoriesFromFile()
                this.activityCategories.clear()
                this.activityCategories.addAll(activityCategoriesFromFile)
                activityCategoriesFromFile
            }
        }
    }

    /**
     * Parses a json file into a list of [ActivityCategoryDTO] and maps
     * it to a list of [ActivityCategory].
     */
    private fun getActivityCategoriesFromFile(): List<ActivityCategory> {
        val jsonString = getJson(FILE_PATH)
        val activityCategoryDTOs = Json.decodeFromString<List<ActivityCategoryDTO>>(jsonString)
        return mapActivityCategoryDTOtoActivityCategory(activityCategoryDTOs)
    }

    /**
     * Maps a list of [ActivityCategoryDTO] to a list of [ActivityCategory].
     */
    private fun mapActivityCategoryDTOtoActivityCategory(
        activityCategoryDTOs: List<ActivityCategoryDTO>
    ): List<ActivityCategory> {
        return activityCategoryDTOs.mapNotNull { activityCategoryDTO ->
            val bfiDimensionsInfo = activityCategoryDTO.bfiDimensionsInfoDTOs?.mapNotNull {
                ActivityCategory.BFIDimensionInfo(
                    id = it.id ?: return@mapNotNull null,
                    regressionsWeight = it.regressionsWeight ?: return@mapNotNull null,
                )
            }

            ActivityCategory(
                id = activityCategoryDTO.id ?: return@mapNotNull null,
                name = activityCategoryDTO.name ?: return@mapNotNull null,
                bfiDimensionsInfo = bfiDimensionsInfo ?: return@mapNotNull null,
            )
        }
    }

    companion object {
        private const val FILE_PATH = "activity_categories.json"
    }
}
