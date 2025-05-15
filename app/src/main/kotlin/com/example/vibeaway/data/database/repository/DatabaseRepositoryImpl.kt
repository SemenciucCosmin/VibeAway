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
            .document(userId)
            .set(scores)
            .addOnSuccessListener { Log.d(TAG, "Firestore write successful") }
            .addOnFailureListener { Log.d(TAG, "Firestore write failure") }
    }

    /**
     * Retrieves user score for each BFI Question in Firestore
     */
    override suspend fun getBFIScores(): Map<String, Int>? {
        val firestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return null

        return try {
            val scores = firestore.collection(USERS_COLLECTION_NAME)
                .document(userId)
                .get()
                .addOnSuccessListener { Log.d(TAG, "Firestore read successful") }
                .addOnFailureListener { Log.d(TAG, "Firestore read failure") }
                .await() as? Map<*, *>

            scores?.mapNotNull {
                val dimensionId = it.key as? String ?: return@mapNotNull null
                val score = (it.value as? Number)?.toInt() ?: return@mapNotNull null
                dimensionId to score
            }?.toMap()
        } catch (_: Exception) {
            null
        }
    }

    companion object {
        private const val TAG = "DatabaseRepository"
        private const val USERS_COLLECTION_NAME = "users"
    }
}
