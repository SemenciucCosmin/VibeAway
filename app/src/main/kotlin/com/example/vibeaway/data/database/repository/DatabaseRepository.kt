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
     * Removes user score for each BFI Question in Firestore
     */
    suspend fun removeBFIScores()

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

    /**
     * Saves activity by [activityId] which is favourite by user
     */
    suspend fun saveFavouriteActivity(activityId: String)

    /**
     * Removes activity by [activityId] which is favourite by user
     */
    suspend fun removeFavouriteActivity(activityId: String)

    /**
     * Retrieves user favourite activity IDs
     */
    suspend fun getFavouriteActivityIds(): List<String>
}
