package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityDTO(
    @SerialName("id") val id: String?,
    @SerialName("description") val description: String?,
    @SerialName("categoryId") val categoryId: String?,
    @SerialName("estimatedCorrelation") val estimatedCorrelation: Double?,
)
