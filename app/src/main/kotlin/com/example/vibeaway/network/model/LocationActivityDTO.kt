package com.example.vibeaway.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for location activity model.
 * @param [id]: unique identifier for location activity object
 * @param [type]: type of location activity
 * @param [name]: name of the location activity
 * @param [shortDescription]: description of the location activity
 * @param [coordinatesDTO]: coordinates of the location activity
 * @param [rating]: rating of the location activity
 * @param [pictures]: list of links to pictures of location activity
 * @param [bookingLink]: link that offers possibility of booking at location activity
 * @param [priceDTO]: price for location activity
 */
@Serializable
data class LocationActivityDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("geoCode") val coordinatesDTO: CoordinatesDTO? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("pictures") val pictures: List<String>? = null,
    @SerialName("bookingLink") val bookingLink: String? = null,
    @SerialName("price") val priceDTO: PriceDTO? = null,
) {
    /**
     * DTO data class for coordinates model.
     * @param [latitude]: latitude coordinate of the location model
     * @param [longitude]: latitude coordinate of the location model
     */
    @Serializable
    data class CoordinatesDTO(
        @SerialName("latitude") val latitude: Double? = null,
        @SerialName("longitude") val longitude: Double? = null,
    )

    /**
     * DTO data class for price model.
     * @param [currencyCode]: price currency code
     * @param [amount]: price amount
     */
    @Serializable
    data class PriceDTO(
        @SerialName("currencyCode") val currencyCode: String? = null,
        @SerialName("amount") val amount: String? = null,
    )
}
