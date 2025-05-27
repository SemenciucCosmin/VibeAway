package com.example.vibeaway.data.locationdetails.model

/**
 * Data class for extended location model
 * @param [id]: unique identifier of the location model
 * @param [city]: city of the location model
 * @param [country]: country of the location model
 * @param [latitude]: latitude coordinate of the location model
 * @param [longitude]: latitude coordinate of the location model
 * @param [imageUrl]: image url of the location model
 */
data class LocationDetails(
    val id: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?,
    val rating: Float,
    val activitiesIds: List<String>
)
