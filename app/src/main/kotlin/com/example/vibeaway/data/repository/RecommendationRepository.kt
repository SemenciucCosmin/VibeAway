package com.example.vibeaway.data.repository

import com.example.vibeaway.data.activity.model.Activity
import com.example.vibeaway.data.activity.model.ActivityInfo
import com.example.vibeaway.data.activitydetails.model.ActivityDetails
import com.example.vibeaway.data.location.model.Location
import com.example.vibeaway.data.locationdetails.model.LocationDetails

/**
 * Repository responsible with computing a set of compatible [Location] with a BFI dataset, using
 * multiple datasets that include [ActivityInfo], [Activity] etc.
 */
interface RecommendationRepository {

    suspend fun getRecommendedActivitiesDetails(): List<ActivityDetails>

    suspend fun getRecommendedLocationsDetails(): List<LocationDetails>

    suspend fun getLocationsDetails(): List<LocationDetails>
}
