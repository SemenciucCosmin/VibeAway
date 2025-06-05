package com.example.vibeaway.data.recommendation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchedActivityDTO(
    @SerialName("id") val id: String?,
    @SerialName("score") val score: String?,
)
