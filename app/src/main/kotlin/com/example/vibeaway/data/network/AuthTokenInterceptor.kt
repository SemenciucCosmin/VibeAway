package com.example.vibeaway.data.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor class for attaching the Amadeus auth bearer token to the http client
 */
class AuthTokenInterceptor(
    private val authTokenManager: AuthTokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authTokenManager.token
        val newRequest = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(newRequest)
    }
}
