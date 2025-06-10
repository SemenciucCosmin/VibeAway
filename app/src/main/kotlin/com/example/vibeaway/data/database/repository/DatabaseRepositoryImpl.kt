package com.example.vibeaway.data.database.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

/**
 * Repository for database storage on Firestore
 */
class DatabaseRepositoryImpl : DatabaseRepository {
    /**
     * Saves user score for each BFI Question in Firestore
     */
    override suspend fun saveBFIScores(scores: Map<String, Int>) {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + BFI_SCORES_DOCUMENT)
            .set(scores)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    /**
     * Retrieves user score for each BFI Question in Firestore
     */
    override suspend fun getBFIScores(): Map<String, Int>? {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return null

        return try {
            val documentSnapshot = firestore.collection(USERS_COLLECTION_NAME)
                .document(userId + BFI_SCORES_DOCUMENT)
                .get()
                .addOnSuccessListener { Log.d(TAG, "Firestore read successful") }
                .addOnFailureListener { Log.d(TAG, "Firestore read failure") }
                .await()

            val scores = documentSnapshot.data as? Map<*, *>

            scores?.mapNotNull {
                val dimensionId = it.key as? String ?: return@mapNotNull null
                val score = (it.value as? Number)?.toInt() ?: return@mapNotNull null
                dimensionId to score
            }?.toMap()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun removeBFIScores() {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + BFI_SCORES_DOCUMENT)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    override suspend fun saveFavouriteLocation(locationId: String) {
        val newFavouriteLocations = getFavouriteLocationIds().toMutableList().apply {
            add(locationId)
        }.toSet().associateWith { true }

        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + FAVOURITE_LOCATIONS_DOCUMENT)
            .set(newFavouriteLocations)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    override suspend fun removeFavouriteLocation(locationId: String) {
        val newFavouriteLocations = getFavouriteLocationIds().toMutableList().apply {
            remove(locationId)
        }.toSet().associateWith { true }

        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + FAVOURITE_LOCATIONS_DOCUMENT)
            .set(newFavouriteLocations)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    override suspend fun getFavouriteLocationIds(): List<String> {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        return try {
            val documentSnapshot = firestore.collection(USERS_COLLECTION_NAME)
                .document(userId + FAVOURITE_LOCATIONS_DOCUMENT)
                .get()
                .addOnSuccessListener { Log.d(TAG, "Firestore read successful") }
                .addOnFailureListener { Log.d(TAG, "Firestore read failure") }
                .await()

            val locations = documentSnapshot.data as? Map<*, *>

            locations?.mapNotNull {
                it.key as? String ?: return@mapNotNull null
            } ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun saveFavouriteActivity(activityId: String) {
        val newFavouriteLocations = getFavouriteLocationIds().toMutableList().apply {
            add(activityId)
        }.toSet().associateWith { true }

        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + FAVOURITE_ACTIVITIES_DOCUMENT)
            .set(newFavouriteLocations)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    override suspend fun removeFavouriteActivity(activityId: String) {
        val newFavouriteLocations = getFavouriteLocationIds().toMutableList().apply {
            remove(activityId)
        }.toSet().associateWith { true }

        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        firestore.collection(USERS_COLLECTION_NAME)
            .document(userId + FAVOURITE_ACTIVITIES_DOCUMENT)
            .set(newFavouriteLocations)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
            .await()
    }

    override suspend fun getFavouriteActivityIds(): List<String> {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        return try {
            val documentSnapshot = firestore.collection(USERS_COLLECTION_NAME)
                .document(userId + FAVOURITE_ACTIVITIES_DOCUMENT)
                .get()
                .addOnSuccessListener { Log.d(TAG, "Firestore read successful") }
                .addOnFailureListener { Log.d(TAG, "Firestore read failure") }
                .await()

            val locations = documentSnapshot.data as? Map<*, *>

            locations?.mapNotNull {
                it.key as? String ?: return@mapNotNull null
            } ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val TAG = "DatabaseRepository"
        private const val USERS_COLLECTION_NAME = "users"
        private const val BFI_SCORES_DOCUMENT = "bfi_scores_document"
        private const val FAVOURITE_LOCATIONS_DOCUMENT = "favourite_locations_document"
        private const val FAVOURITE_ACTIVITIES_DOCUMENT = "favourite_activities_document"
    }
}
