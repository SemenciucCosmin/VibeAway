package com.example.vibeaway.data.auth.model

/**
 * Data class for firebase user model
 * @param [id]: unique identifier for user object
 * @param [email]: user email
 * @param [provider]: ??
 * @param [displayName]: user display name
 * @param [isAnonymous]: whether the user has an account or is anonymous registered
 */
data class FirebaseUser(
    val id: String,
    val email: String,
    val provider: String,
    val displayName: String,
    val isAnonymous: Boolean
)
