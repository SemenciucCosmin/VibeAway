package com.example.vibeaway.ui.navigation.model

import kotlinx.serialization.Serializable

/**
 * Sealed class for all possible navigation destination from recommendation flow.
 */
@Serializable
sealed class RecommendationNavDestination {

    @Serializable
    data object Feed

    @Serializable
    data class Category(val id: Int)

    @Serializable
    data class ActivityDetails(val id: String)

    @Serializable
    data class LocationDetails(val id: String)
}
