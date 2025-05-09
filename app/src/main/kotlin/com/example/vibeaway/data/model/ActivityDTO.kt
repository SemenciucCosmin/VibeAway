package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("categoryId") val categoryId: String? = null,
    @SerialName("estimatedCorrelation") val estimatedCorrelation: Double? = null,
)
