package com.example.vibeaway.data.activity.datasource

import com.example.vibeaway.data.activity.model.ActivityCategoryDTO
import com.example.vibeaway.data.activity.model.ActivityInfo
import com.example.vibeaway.data.core.datasource.JsonDataSource
import kotlinx.serialization.json.Json
import java.util.Collections

/**
 * Data source for providing the list of [ActivityInfo] from a json file or from in memory cache.
 */
class ActivityCategoriesDataSourceImpl : ActivityCategoriesDataSource, JsonDataSource() {

    /**
     * In memory cache for list of [ActivityInfo]
     */
    private val activityCategories: MutableList<ActivityInfo> =
        Collections.synchronizedList(mutableListOf())

    /**
     * Parses a json file into a list of [ActivityInfo] or retrieves it from in memory cache.
     */
    override fun getActivityCategories(): List<ActivityInfo> {
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
     * it to a list of [ActivityInfo].
     */
    private fun getActivityCategoriesFromFile(): List<ActivityInfo> {
        val jsonString = getJsonFromResources(FILE_PATH)
        val activityCategoryDTOs = Json.decodeFromString<List<ActivityCategoryDTO>>(jsonString)
        return mapActivityCategoryDTOtoActivityCategory(activityCategoryDTOs)
    }

    /**
     * Maps a list of [ActivityCategoryDTO] to a list of [ActivityInfo].
     */
    private fun mapActivityCategoryDTOtoActivityCategory(
        activityCategoryDTOs: List<ActivityCategoryDTO>
    ): List<ActivityInfo> {
        return activityCategoryDTOs.mapNotNull { activityCategoryDTO ->
            val bfiDimensionsInfo = activityCategoryDTO.bfiDimensionsInfoDTOs?.mapNotNull {
                ActivityInfo.BFIDimensionInfo(
                    id = it.id ?: return@mapNotNull null,
                    regressionsWeight = it.regressionsWeight ?: return@mapNotNull null,
                )
            }

            ActivityInfo(
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
