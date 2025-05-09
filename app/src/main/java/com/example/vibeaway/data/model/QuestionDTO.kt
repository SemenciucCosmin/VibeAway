package com.example.vibeaway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDTO(
    @SerialName("id") val id: Int?,
    @SerialName("text") val text: String?,
    @SerialName("bfiDimensionId") val bfiDimensionId: String?,
    @SerialName("reverseScore") val reverseScore: Boolean?
)
