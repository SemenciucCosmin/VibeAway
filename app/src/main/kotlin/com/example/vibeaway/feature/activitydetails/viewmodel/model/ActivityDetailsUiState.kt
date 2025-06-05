package com.example.vibeaway.feature.activitydetails.viewmodel.model

data class ActivityDetailsUiState(
    val currentActivity: Activity? = null,
    val relatedActivities: List<Activity> = emptyList()
) {
    data class Activity(
        val id: String,
        val title: String,
        val imageUrl: String?,
        val latitude: Double,
        val longitude: Double,
        val rating: Double,
        val locationId: String,
        val locationCity: String,
        val locationCountry: String,
        val isFavourite: Boolean,
    )
}
