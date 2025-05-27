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

    /**
     * Saves location by [locationId] which is favourite by user
     */
    suspend fun saveFavouriteLocation(locationId: String)

    /**
     * Removes location by [locationId] which is favourite by user
     */
    suspend fun removeFavouriteLocation(locationId: String)

    /**
     * Retrieves user favourite location IDs
     */
    suspend fun getFavouriteLocationIds(): List<String>
}
