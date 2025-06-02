package com.example.vibeaway.feature.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.domain.locationdetails.usecase.GetPopularLocationDetailsUseCase
import com.example.vibeaway.feature.feed.viewmodel.model.FeedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View model class for feed screen on recommendation screen
 */
class FeedViewModel(
    private val getPopularLocationDetailsUseCase: GetPopularLocationDetailsUseCase,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()
        .onStart { getPopularLocationDetails() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    private fun getPopularLocationDetails() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }

        val favouriteLocationsIds = databaseRepository.getFavouriteLocationIds()
        val popularLocationDetails = getPopularLocationDetailsUseCase().map {
            FeedUiState.LocationDetails(
                id = it.id,
                imageFileName = it.imageFileName,
                isFavourite = favouriteLocationsIds.contains(it.id),
                city = it.city,
                country = it.country
            )
        }

        val restrictedPopularLocationDetails = when {
            popularLocationDetails.size < POPULAR_LOCATION_DETAILS_COUNT -> popularLocationDetails
            else -> popularLocationDetails.take(POPULAR_LOCATION_DETAILS_COUNT)
        }

        _uiState.update {
            it.copy(
                locationsDetails = restrictedPopularLocationDetails,
                isLoading = false,
            )
        }
    }

    fun changeLocationDetailsFavouriteState(locationDetailsId: String) = viewModelScope.launch {
        _uiState.update {
            val updatedLocationsDetails = it.locationsDetails.map { locationDetails ->
                when {
                    locationDetails.id == locationDetailsId && locationDetails.isFavourite -> {
                        databaseRepository.removeFavouriteLocation(locationDetailsId)
                        locationDetails.copy(isFavourite = false)
                    }

                    locationDetails.id == locationDetailsId -> {
                        databaseRepository.saveFavouriteLocation(locationDetailsId)
                        locationDetails.copy(isFavourite = true)
                    }

                    else -> locationDetails
                }
            }

            it.copy(locationsDetails = updatedLocationsDetails)
        }
    }

    companion object {
        const val POPULAR_LOCATION_DETAILS_COUNT = 6
    }
}
