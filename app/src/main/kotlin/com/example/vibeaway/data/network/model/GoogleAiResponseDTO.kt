package com.example.vibeaway.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAiResponseDTO(
    @SerialName("candidates") val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    @SerialName("content") val content: Content
)
