package com.example.vibeaway.data.network.datasource

/**
 * Data source class for initializing the authentication needed for Amadeus API calls.
 */
interface AuthTokenDataSource {

    /**
     * Retrieves the Amadeus authentication token from API using API key and secret.
     */
    fun initialize()
}
