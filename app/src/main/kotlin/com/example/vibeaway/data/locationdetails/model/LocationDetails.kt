package com.example.vibeaway.data.locationdetails.model

import com.example.vibeaway.data.activitydetails.model.ActivityDetails

/**
 * Data class for extended location model
 * @param [id]: unique identifier of the location model
 * @param [city]: city of the location model
 * @param [country]: country of the location model
 * @param [latitude]: latitude coordinate of the location model
 * @param [longitude]: latitude coordinate of the location model
 * @param [imageFileName]: image file name of the location model
 */
data class LocationDetails(
    val id: String,
    val city: String,
    val country: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageFileName: String?,
    val activitiesDetails: List<ActivityDetails>
)
