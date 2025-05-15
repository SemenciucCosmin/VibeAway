package com.example.vibeaway.data.database.repository

/**
 * Repository for database storage on Firestore
 */
interface DatabaseRepository {
    /**
     * Saves user score for each BFI Question in Firestore
     */
    suspend fun saveBFIScores(scores: Map<String, Int>)

    /**
     * Retrieves user score for each BFI Question in Firestore
     */
    suspend fun getBFIScores(): Map<String, Int>?
}
