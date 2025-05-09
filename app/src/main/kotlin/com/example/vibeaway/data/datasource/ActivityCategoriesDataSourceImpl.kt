package com.example.vibeaway.data.datasource

import com.example.vibeaway.data.model.ActivityCategory
import com.example.vibeaway.data.model.ActivityCategoryDTO
import kotlinx.serialization.json.Json
import java.util.Collections

class ActivityCategoriesDataSourceImpl : ActivityCategoriesDataSource, JsonDataSource() {

    private val activityCategories: MutableList<ActivityCategory> =
        Collections.synchronizedList(mutableListOf())

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

    private fun getActivityCategoriesFromFile(): List<ActivityCategory> {
        val jsonString = getJson(FILE_PATH)
        val activityCategoryDTOs = Json.decodeFromString<List<ActivityCategoryDTO>>(jsonString)
        return mapActivityCategoryDTOtoActivityCategory(activityCategoryDTOs)
    }

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
