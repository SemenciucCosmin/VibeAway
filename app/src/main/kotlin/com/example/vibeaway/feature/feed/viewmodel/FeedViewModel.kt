package com.example.vibeaway.feature.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlin.random.Random

/**
 * View model class for feed screen on recommendation screen
 */
class FeedViewModel(
    private val getPopularLocationDetailsUseCase: GetPopularLocationDetailsUseCase
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

        val popularLocationDetails = getPopularLocationDetailsUseCase().map {
            FeedUiState.LocationDetails(
                id = it.id,
                imageUrl = it.imageUrl,
                isFavourite = Random.nextBoolean(),
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
                when (locationDetails.id) {
                    locationDetailsId -> locationDetails.copy(
                        isFavourite = !locationDetails.isFavourite
                    )

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
