package com.example.vibeaway.data.model

/**
 * Data class for an activity that is determined to be compatible with a BFI dataset
 * through a mathematical algorithm
 * @param [id]: unique identifier of the object
 * @param [score]: value that determines the overall activity compatibility
 * with the BFI dataset
 */
data class CompatibleActivity(
    val id: String,
    val score: Double
)
