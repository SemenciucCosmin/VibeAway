package com.example.vibeaway.data.network

import com.example.vibeaway.data.network.service.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val amadeusApiKey: String,
    private val amadeusApiSecret: String,
    private val authTokenManager: AuthTokenManager,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // If we’ve already tried with the token and it failed, don’t retry again
        if (responseCount(response) >= 2) return null

        synchronized(this) {
            val currentToken = authTokenManager.token

            // If token was refreshed by another thread, retry with new one
            if (response.request.header("Authorization") != "Bearer $currentToken") {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${authTokenManager.token}")
                    .build()
            }

            // Refresh token synchronously
            val tokenResponse = runBlocking {
                authApi.getAuthenticationBearerToken(
                    clientId = amadeusApiKey,
                    clientSecret = amadeusApiSecret
                )
            }

            if (tokenResponse.isSuccessful) {
                val newToken = tokenResponse.body()?.accessToken ?: return null
                authTokenManager.token = newToken

                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            }

            return null // Failed to refresh
        }
    }

    private fun responseCount(response: Response): Int {
        var res: Response? = response
        var count = 1
        while (res?.priorResponse != null) {
            count++
            res = res.priorResponse
        }
        return count
    }
}
