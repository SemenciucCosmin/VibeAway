package com.example.vibeaway.feature.locationdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.locationdetails.datasource.LocationDetailsDataSource
import com.example.vibeaway.feature.locationdetails.viewmodel.model.LocationDetailsUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val locationDetailsId: String,
    private val locationDetailsDataSource: LocationDetailsDataSource,
    private val activityDetailsDataSource: ActivityDetailsDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationDetailsUiState())
    val uiState: StateFlow<LocationDetailsUiState> = _uiState.asStateFlow()
        .onStart { getLocationDetails() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    private fun getLocationDetails() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        val locationsDetailsAsync = async { locationDetailsDataSource.getLocationsDetails() }
        val activitiesDetailsAsync = async { activityDetailsDataSource.getActivitiesDetails() }

        val locationsDetails = locationsDetailsAsync.await()
        val activitiesDetails = activitiesDetailsAsync.await()

        val locationDetails = locationsDetails.firstOrNull {
            it.id == locationDetailsId
        } ?: return@launch

        val locationDetailsActivities = activitiesDetails.filter { activity ->
            activity.id in locationDetails.activitiesIds
        }.map { activity ->
            LocationDetailsUiState.Activity(
                id = activity.id,
                title = activity.title,
                description = activity.description,
                imageUrl = activity.imageUrl
            )
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                city = locationDetails.city,
                country = locationDetails.country,
                imageUrl = locationDetails.imageUrl,
                description = locationDetails.description,
                latitude = locationDetails.latitude,
                longitude = locationDetails.longitude,
                activities = locationDetailsActivities,
            )
        }
    }
}