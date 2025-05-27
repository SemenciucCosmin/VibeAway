package com.example.vibeaway.data.activity.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for activity model
 * @param [id]: unique identifier of the object
 * @param [description]: description of the activity model
 * @param [categoryId]: [ActivityCategory] to which this activity is related
 * @param [estimatedCorrelation]: coefficient determining the level of correlation between this
 * activity and the [ActivityCategory] specified by [categoryId]
 */
@Serializable
data class ActivityDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("categoryId") val categoryId: String? = null,
    @SerialName("estimatedCorrelation") val estimatedCorrelation: Double? = null,
)
