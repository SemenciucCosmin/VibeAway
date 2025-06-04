package com.example.vibeaway.data.activitydetails.datasource

import com.example.vibeaway.data.activitydetails.model.ActivityDetails

/**
 * Data source for providing the list of [ActivityDetails]
 */
interface ActivityDetailsDataSource {

    suspend fun getActivitiesDetails(
        forceApi: Boolean,
        forceGoogle: Boolean,
    ): Map<String, List<ActivityDetails>>
}
