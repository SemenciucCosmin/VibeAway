package com.example.vibeaway.data.activitydetails.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for activity details model
 * @param [id]: id of the activity details model
 * @param [locationId]: id of the location to which the activity details model belongs
 * @param [name]: name of the activity details model
 * @param [description]: description of the activity details model
 * @param [latitude]: latitude coordinate
 * @param [longitude]: longitude coordinate
 * @param [rating]: rating of the activity details model
 * @param [imageUrl]: image url of the activity details model
 * @param [bookingUrl]: bookingUrl of the activity details model
 * @param [currencyCode]: currency code
 * @param [priceAmount]: price amount
 */
@Serializable
data class ActivityDetailsFileDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("locationId") val locationId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("shortDescription") val description: String? = null,
    @SerialName("latitude") val latitude: Double? = null,
    @SerialName("longitude") val longitude: Double? = null,
    @SerialName("rating") val rating: Float? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("bookingLink") val bookingUrl: String? = null,
    @SerialName("currencyCode") val currencyCode: String? = null,
    @SerialName("priceAmount") val priceAmount: Double? = null,
)
