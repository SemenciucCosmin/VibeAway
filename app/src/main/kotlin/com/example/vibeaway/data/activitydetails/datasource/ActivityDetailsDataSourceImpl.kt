package com.example.vibeaway.data.activitydetails.datasource

import com.example.vibeaway.data.activitydetails.model.ActivityDetails

/**
 * Data source for providing the list of [ActivityDetails]
 */
class ActivityDetailsDataSourceImpl : ActivityDetailsDataSource {

    override fun getActivitiesDetails(): List<ActivityDetails> {
        return List(20) {
            ActivityDetails(
                id = it.toString(),
                description = "ActivityDetails: $it"
            )
        }
    }
}
