package com.example.vibeaway.data.activity.model

/**
 * Data class for an activity category that is determined to be compatible with a BFI dataset
 * through a mathematical algorithm
 * @param [id]: unique identifier of the object
 * @param [score]: value that determines the overall activity category compatibility
 * with the BFI dataset
 */
data class CompatibleActivityCategory(
    val id: String,
    val score: Double
)
