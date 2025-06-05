package com.example.vibeaway.data.repository

import com.example.vibeaway.data.activity.datasource.ActivitiesDataSource
import com.example.vibeaway.data.activity.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.activity.model.Activity
import com.example.vibeaway.data.activity.model.ActivityInfo
import com.example.vibeaway.data.activity.model.CompatibleActivity
import com.example.vibeaway.data.activity.model.CompatibleActivityCategory
import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.location.model.Location
import com.example.vibeaway.data.locationdetails.model.LocationDetails
import com.example.vibeaway.data.quiz.model.BFIResult

/**
 * Repository responsible with computing a set of compatible [Location] with a BFI dataset, using
 * multiple datasets that include [ActivityInfo], [Activity] etc.
 */
class RecommendationRepositoryImpl(
    private val activityCategoriesDataSource: ActivityCategoriesDataSource,
    private val activitiesDataSource: ActivitiesDataSource,
    private val locationsDataSource: LocationsDataSource,
    private val activityDetailsDataSource: ActivityDetailsDataSource,
) : RecommendationRepository {

    /**
     * Returns a list of [CompatibleActivityCategory] by computing some correlations between
     * a dataset of [BFIResult] and a dataset of [ActivityInfo]
     */
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
                score = score
            )
        }
    }

    override suspend fun getRecommendedActivitiesDetails(): List<ActivityDetails> {
        return getActivitiesDetails()
    }

    override suspend fun getRecommendedLocationsDetails(): List<LocationDetails> {
        return getLocationsDetails()
    }

    override suspend fun getLocationsDetails(): List<LocationDetails> {
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

    private suspend fun getActivitiesDetails(): List<ActivityDetails> {
        return activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        ).values.flatten()
    }
}
