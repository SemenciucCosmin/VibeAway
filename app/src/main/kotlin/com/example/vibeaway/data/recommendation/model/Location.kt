package com.example.vibeaway.data.recommendation.model

/**
 * Data class for location model
 * @param [city]: city of the location model
 * @param [country]: country of the location model
 * @param [latitude]: latitude coordinate of the location model
 * @param [longitude]: latitude coordinate of the location model
 */
data class Location(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)
