package com.example.vibeaway.data.recommendation.model

/**
 * Data class for activity model
 * @param [id]: unique identifier of the object
 * @param [description]: description of the activity model
 * @param [categoryId]: [ActivityCategory] to which this activity is related
 * @param [estimatedCorrelation]: coefficient determining the level of correlation between this
 * activity and the [ActivityCategory] specified by [categoryId]
 */
data class Activity(
    val id: String,
    val description: String,
    val categoryId: String,
    val estimatedCorrelation: Double,
)
