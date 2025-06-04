package com.example.vibeaway.data.activitydetails.model

/**
 * Data class for extended activity model
 * @param [id]: unique identifier of the object
 * @param [description]: description of the activity model
 */
data class ActivityDetails(
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val priceAmount: Double?,
    val currencyCode: String?,
    val bookingUrl: String?,
    val rating: Double,
    val types: List<String>,
)
