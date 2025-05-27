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
    data object Category

    @Serializable
    data object ActivityDetails

    @Serializable
    data class LocationDetails(val id: String)
}
