package com.example.vibeaway.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleAiRequestDTO(
    @SerialName("contents") val contents: List<Content>
)

@Serializable
data class Content(
    @SerialName("role") val role: String,
    @SerialName("parts") val parts: List<Part>
)

@Serializable
data class Part(
    @SerialName("text") val text: String
)
