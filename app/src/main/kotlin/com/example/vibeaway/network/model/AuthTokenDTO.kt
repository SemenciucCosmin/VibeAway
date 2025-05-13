package com.example.vibeaway.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO class for auth token response from Amadeus API
 */
@Serializable
data class AuthTokenDTO(
    @SerialName("type") val type: String? = null,
    @SerialName("username") val username: String? = null,
    @SerialName("application_name") val applicationName: String? = null,
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("token_type") val tokenType: String? = null,
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("expires_in") val expiresIn: Int? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("scope") val scope: String? = null,
)
