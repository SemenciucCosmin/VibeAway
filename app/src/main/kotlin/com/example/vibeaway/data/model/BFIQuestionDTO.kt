package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BFIQuestionDTO(
    @SerialName("id") val id: Int? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("bfiDimensionId") val bfiDimensionId: String? = null,
    @SerialName("reverseScore") val reverseScore: Boolean = false
)
