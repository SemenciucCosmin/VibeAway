package com.example.vibeaway.data.repository

import com.example.vibeaway.data.activity.model.Activity
import com.example.vibeaway.data.activity.model.ActivityCategory
import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.location.model.Location
import com.example.vibeaway.data.locationdetails.model.LocationDetails

/**
 * Repository responsible with computing a set of compatible [Location] with a BFI dataset, using
 * multiple datasets that include [ActivityCategory], [Activity] etc.
 */
interface RecommendationRepository {

    suspend fun getRecommendedLocationsDetails(): List<LocationDetails>

    suspend fun getRecommendedActivitiesDetails(): List<ActivityDetails>
}
