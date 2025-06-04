package com.example.vibeaway.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO data class for activity details model
 * @param [id]: id of the activity details model
 * @param [name]: name of the activity details model
 * @param [description]: description of the activity details model
 * @param [geolocation]: geolocation of the activity details model
 * @param [rating]: rating of the activity details model
 * @param [imagesUrls]: list of image urls of the activity details model
 * @param [bookingUrl]: bookingUrl of the activity details model
 * @param [price]: price of the activity details model
 */
@Serializable
data class ActivityDetailsDTO(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("shortDescription") val description: String? = null,
    @SerialName("geoCode") val geolocation: GeolocationDTO? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("pictures") val imagesUrls: List<String?> = emptyList(),
    @SerialName("bookingLink") val bookingUrl: String? = null,
    @SerialName("price") val price: PriceDTO? = null,
) {
    /** DTO data class for geolocation model
     * @param [latitude]: latitude coordinate
     * @param [longitude]: latitude coordinate
     */
    @Serializable
    data class GeolocationDTO(
        @SerialName("latitude") val latitude: Double? = null,
        @SerialName("longitude") val longitude: Double? = null,
    )

    /** DTO data class for price model
     * @param [currencyCode]: currency code
     * @param [amount]: price amount
     */
    @Serializable
    data class PriceDTO(
        @SerialName("currencyCode") val currencyCode: String? = null,
        @SerialName("amount") val amount: Double? = null,
    )
}
