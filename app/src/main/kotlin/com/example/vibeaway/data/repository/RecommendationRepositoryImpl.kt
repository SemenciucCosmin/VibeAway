package com.example.vibeaway.data.repository

import com.example.vibeaway.data.datasource.ActivitiesDataSource
import com.example.vibeaway.data.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.model.BFIResult
import com.example.vibeaway.data.model.CompatibleActivity
import com.example.vibeaway.data.model.CompatibleActivityCategory

class RecommendationRepositoryImpl(
    private val activityCategoriesDataSource: ActivityCategoriesDataSource,
    private val activitiesDataSource: ActivitiesDataSource,
) : RecommendationRepository {

    private fun getCompatibleActivityCategories(
        bfiResults: List<BFIResult>
    ): List<CompatibleActivityCategory> {
        val activityCategories = activityCategoriesDataSource.getActivityCategories()

        return activityCategories.map { activityCategory ->
            val scores = activityCategory.bfiDimensionsInfo.mapNotNull { bfiDimensionInfo ->
                val bfiDimensionResult = bfiResults.firstOrNull { bFIResult ->
                    bFIResult.bfiDimension.id == bfiDimensionInfo.id
                } ?: return@mapNotNull null

                bfiDimensionResult.score * bfiDimensionInfo.regressionsWeight
            }

            CompatibleActivityCategory(
                id = activityCategory.id,
                score = scores.sum()
            )
        }
    }

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
                compatibilityScore = score
            )
        }
    }
}
