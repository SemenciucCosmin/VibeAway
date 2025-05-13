package com.example.vibeaway.data.auth.repository

import com.example.vibeaway.data.auth.model.FirebaseUser
import com.example.vibeaway.domain.core.string.BLANK
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repository class for user authentication actions and information
 */
class AuthRepositoryImpl : AuthRepository {

    /**
     * Returns a flow with the logged [FirebaseUser] or null is no user is logged in
     */
    override suspend fun getCurrentUserFlow(): Flow<FirebaseUser?> {
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                val firebaseUser = auth.currentUser?.let { firebaseUser ->
                    val email = firebaseUser.email ?: return@let null
                    val displayName = firebaseUser.displayName ?: String.BLANK

                    FirebaseUser(
                        id = firebaseUser.uid,
                        email = email,
                        provider = firebaseUser.providerId,
                        displayName = displayName,
                        isAnonymous = firebaseUser.isAnonymous
                    )
                }

                this.trySend(firebaseUser)
            }

            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }
    }

    /**
     * Returns the logged [FirebaseUser] or null is no user is logged in
     */
    override fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser?.let { firebaseUser ->
            val email = firebaseUser.email ?: return@let null
            val displayName = firebaseUser.displayName ?: String.BLANK

            FirebaseUser(
                id = firebaseUser.uid,
                email = email,
                provider = firebaseUser.providerId,
                displayName = displayName,
                isAnonymous = firebaseUser.isAnonymous
            )
        }
    }

    /**
     * Uses the [email] and [password] from user to log in a firebase account
     */
    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Uses the [email] and [password] from user to create a firebase account
     */
    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    /**
     * Uses the [idToken] from google auth flow to log the user in
     */
    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential).await()
    }

    /**
     * Sign the user out
     */
    override fun signOut() {
        Firebase.auth.signOut()
    }

    /**
     * Deletes the user's account
     */
    override suspend fun deleteAccount(): Boolean {
        val firebaseUser = Firebase.auth.currentUser ?: return false
        firebaseUser.delete().await()
        return true
    }
}
