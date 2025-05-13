package com.example.vibeaway.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO class for generic Amadeus API response model.
 * Acts as a wrapper over any response.
 * See https://developers.amadeus.com/self-service/category/flights/api-doc/flight-price-analysis/api-reference
 * @param [meta] response metadata model, containing details about the API call
 * @param [data] actual response data
 */
@Serializable
data class GenericAmadeusDTO<T>(
    @SerialName("meta") val meta: String? = null,
    @SerialName("data") val data: T? = null,
) {
    /**
     * DTO class for response metadata model, containing details about the API call.
     * @param [count] ??
     * @param [link] called link url
     */
    @Serializable
    data class MetaDTO(
        @SerialName("count") val count: Int? = null,
        @SerialName("links") val link: LinkDTO? = null,
    ) {
        /**
         * DTO class for link model
         * @param [self]: link url
         */
        @Serializable
        data class LinkDTO(
            @SerialName("self") val self: String? = null,
        )
    }
}
