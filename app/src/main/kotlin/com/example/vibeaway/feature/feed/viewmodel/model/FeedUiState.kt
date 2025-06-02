package com.example.vibeaway.feature.feed.viewmodel.model

/**
 * Data class as ui state for feed screen from recommendation feature
 */
data class FeedUiState(
    val locationsDetails: List<LocationDetails> = emptyList(),
    val isLoading: Boolean = false,
) {
    /**
     * Data class for popular location details model on feed screen
     */
    data class LocationDetails(
        val id: String,
        val imageFileName: String?,
        val isFavourite: Boolean,
        val city: String,
        val country: String,
    )
}
