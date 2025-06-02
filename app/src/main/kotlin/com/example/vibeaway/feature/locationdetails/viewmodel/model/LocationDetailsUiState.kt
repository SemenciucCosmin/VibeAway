package com.example.vibeaway.feature.locationdetails.viewmodel.model

import com.example.vibeaway.domain.core.string.BLANK

data class LocationDetailsUiState(
    val isLoading: Boolean = false,
    val city: String = String.BLANK,
    val country: String = String.BLANK,
    val imageFileName: String? = null,
    val description: String = String.BLANK,
    val isFavourite: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val activities: List<Activity> = emptyList()
) {
    data class Activity(
        val id: String,
        val title: String,
        val description: String?,
        val imageUrl: String?,
    )
}
