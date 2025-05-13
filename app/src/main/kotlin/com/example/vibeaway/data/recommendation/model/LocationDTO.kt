package com.example.vibeaway.data.recommendation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for location model
 * @param [city]: city of the location model
 * @param [country]: country of the location model
 * @param [latitude]: latitude coordinate of the location model
 * @param [longitude]: latitude coordinate of the location model
 */
@Serializable
data class LocationDTO(
    @SerialName("city") val city: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
)
