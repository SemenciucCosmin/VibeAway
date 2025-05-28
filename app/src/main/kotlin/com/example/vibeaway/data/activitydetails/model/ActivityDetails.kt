package com.example.vibeaway.data.activitydetails.model

/**
 * Data class for extended activity model
 * @param [id]: unique identifier of the object
 * @param [description]: description of the activity model
 */
data class ActivityDetails(
    val id: String,
    val title: String,
    val city: String,
    val description: String,
    val imageUrl: String?
)
