package com.example.vibeaway.data.activity.model

/**
 * Data class for activity model
 * @param [id]: unique identifier of the object
 * @param [description]: description of the activity model
 * @param [categoryId]: [ActivityInfo] to which this activity is related
 * @param [estimatedCorrelation]: coefficient determining the level of correlation between this
 * activity and the [ActivityInfo] specified by [categoryId]
 */
data class Activity(
    val id: String,
    val description: String,
    val categoryId: String,
    val estimatedCorrelation: Double,
)
