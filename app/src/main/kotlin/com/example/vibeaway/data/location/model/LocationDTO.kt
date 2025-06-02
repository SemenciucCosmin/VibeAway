package com.example.vibeaway.data.location.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for location model
 * @param [id]: id of the location model
 * @param [city]: city of the location model
 * @param [country]: country of the location model
 * @param [latitude]: latitude coordinate of the location model
 * @param [longitude]: latitude coordinate of the location model
 * @param [description]: description of the location model
 * @param [imageFileName]: image file name of the location model
 */
@Serializable
data class LocationDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("imageFileName") val imageFileName: String? = null,
)
