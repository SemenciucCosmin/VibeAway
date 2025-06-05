package com.example.vibeaway.feature.locationdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.domain.locationdetails.usecase.GetLocationDetailsUseCase
import com.example.vibeaway.feature.locationdetails.viewmodel.model.LocationDetailsUiState
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
    private val databaseRepository: DatabaseRepository,
    private val getLocationDetailsUseCase: GetLocationDetailsUseCase,
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

        val locationsDetails = getLocationDetailsUseCase()
        val favouriteLocationsIds = databaseRepository.getFavouriteLocationIds()

        val locationDetails = locationsDetails.firstOrNull {
            it.id == locationDetailsId
        } ?: return@launch

        val locationDetailsActivities = locationDetails.activitiesDetails.map { activityDetails ->
            LocationDetailsUiState.Activity(
                id = activityDetails.id,
                title = activityDetails.title,
                description = activityDetails.description,
                imageUrl = activityDetails.imageUrl,
                rating = activityDetails.rating
            )
        }

        _uiState.update {
            it.copy(
                isLoading = false,
                isFavourite = locationDetailsId in favouriteLocationsIds,
                city = locationDetails.city,
                country = locationDetails.country,
                imageFileName = locationDetails.imageFileName,
                description = locationDetails.description,
                latitude = locationDetails.latitude,
                longitude = locationDetails.longitude,
                activities = locationDetailsActivities,
            )
        }
    }

    fun changeFavouriteState() = viewModelScope.launch {
        _uiState.update {
            val isFavourite = !it.isFavourite

            when {
                isFavourite -> databaseRepository.saveFavouriteLocation(locationDetailsId)
                else -> databaseRepository.removeFavouriteLocation(locationDetailsId)
            }

            it.copy(isFavourite = isFavourite)
        }
    }
}
