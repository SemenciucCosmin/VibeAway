package com.example.vibeaway.domain.auth

/**
 * Data source class for initializing the authentication needed for Amadeus API calls.
 */
interface AuthTokenDataSource {
    fun initialize()
}
