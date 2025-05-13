package com.example.vibeaway.data.network.datasource

import com.example.vibeaway.data.network.AuthTokenManager
import com.example.vibeaway.data.network.resource.processApiResource
import com.example.vibeaway.data.network.service.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Data source class for initializing the authentication needed for Amadeus API calls.
 */
class AuthTokenDataSourceImpl(
    private val amadeusApiKey: String,
    private val amadeusApiSecret: String,
    private val authApi: AuthApi,
    private val authTokenManager: AuthTokenManager,
    private val coroutineScope: CoroutineScope,
) : AuthTokenDataSource {

    /**
     * Retrieves the Amadeus authentication token from API using API key and secret.
     */
    override fun initialize() {
        coroutineScope.launch {
            val resource = processApiResource {
                authApi.getAuthenticationBearerToken(
                    clientId = amadeusApiKey,
                    clientSecret = amadeusApiSecret
                )
            }

            authTokenManager.token = resource.payload?.accessToken
        }
    }
}
