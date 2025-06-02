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
        val originalRequest = chain.request()
        val token = authTokenManager.token

        val requestWithToken = if (!token.isNullOrBlank()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(requestWithToken)
    }
}
