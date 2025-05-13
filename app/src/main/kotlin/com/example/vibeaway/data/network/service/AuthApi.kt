package com.example.vibeaway.data.network.service

import com.example.vibeaway.data.network.model.AuthTokenDTO
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Api service for Amadeus auth calls
 * See https://developers.amadeus.com
 */
interface AuthApi {
    /**
     * Retrieves the authentication bearer token for Amadeus API calls
     * from Amadeus API key and secret
     */
    @FormUrlEncoded
    @POST("/v1/security/oauth2/token")
    suspend fun getAuthenticationBearerToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<AuthTokenDTO>
}
