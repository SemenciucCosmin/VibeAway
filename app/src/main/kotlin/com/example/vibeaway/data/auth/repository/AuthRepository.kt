package com.example.vibeaway.data.auth.repository

import com.example.vibeaway.data.auth.model.FirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for user authentication actions and information
 */
interface AuthRepository {

    /**
     * Returns a flow with the logged [FirebaseUser] or null is no user is logged in
     */
    suspend fun getCurrentUserFlow(): Flow<FirebaseUser?>

    /**
     * Returns the logged [FirebaseUser] or null is no user is logged in
     */
    fun getCurrentUser(): FirebaseUser?

    /**
     * Uses the [email] and [password] from user to log in a firebase account
     */
    suspend fun signIn(email: String, password: String)

    /**
     * Uses the [email] and [password] from user to create a firebase account
     */
    suspend fun signUp(email: String, password: String)

    /**
     * Uses the [idToken] from google auth flow to log the user in
     */
    suspend fun signInWithGoogle(idToken: String)

    /**
     * Sign the user out
     */
    fun signOut()

    /**
     * Deletes the user's account
     */
    suspend fun deleteAccount(): Boolean
}
