package com.example.vibeaway.data.auth.datasource

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

/**
 * Data source for google credential authentication
 */
class GoogleCredentialDataSource(
    private val context: Context,
    private val defaultWebClientId: String,
) {

    /**
     * Retrieves Google [Credential] using googleId identity library for Google auth on Firebase.
     */
    suspend fun getCredential(): Credential? {
        val credentialManager = CredentialManager.create(context)

        val goggleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(defaultWebClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(goggleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            return result.credential
        } catch (exception: GetCredentialException) {
            Log.d(TAG, "Credential exception: ${exception.message}, ${exception.cause}")
            return null
        }
    }

    companion object {
        private const val TAG = "GoogleCredentialDataSource"
    }
}
