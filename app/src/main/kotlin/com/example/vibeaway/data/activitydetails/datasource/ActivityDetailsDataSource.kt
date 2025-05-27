package com.example.vibeaway.data.activitydetails.datasource

import com.example.vibeaway.data.activitydetails.model.ActivityDetails

/**
 * Data source for providing the list of [ActivityDetails]
 */
interface ActivityDetailsDataSource {

    fun getActivitiesDetails(): List<ActivityDetails>
}
