package com.example.vibeaway.data.auth.model

/**
 * Data class for firebase user model
 */
data class FirebaseUser(
    val id: String,
    val email: String,
    val provider: String,
    val displayName: String,
    val isAnonymous: Boolean
)
