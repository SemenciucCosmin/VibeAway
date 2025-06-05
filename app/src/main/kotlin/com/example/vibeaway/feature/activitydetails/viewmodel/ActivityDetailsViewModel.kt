package com.example.vibeaway.feature.activitydetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.domain.locationdetails.usecase.GetLocationDetailsUseCase
import com.example.vibeaway.feature.activitydetails.viewmodel.model.ActivityDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivityDetailsViewModel(
    private val activityDetailsId: String,
    private val databaseRepository: DatabaseRepository,
    private val activityDetailsDataSource: ActivityDetailsDataSource,
    private val getLocationDetailsUseCase: GetLocationDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDetailsUiState())
    val uiState: StateFlow<ActivityDetailsUiState> = _uiState.asStateFlow()
        .onStart { getActivityDetails() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    private fun getActivityDetails() = viewModelScope.launch {
        val locationsDetails = getLocationDetailsUseCase()
        val favouriteActivityIds = databaseRepository.getFavouriteActivityIds()
        val allActivityDetails = activityDetailsDataSource.getActivitiesDetails(
            forceApi = false,
            forceGoogle = false
        )

        val activityDetailsEntry =
            allActivityDetails.entries.firstOrNull { (_, activitiesDetails) ->
                activityDetailsId in activitiesDetails.map { it.id }
            }

        val location = locationsDetails.firstOrNull { it.id == activityDetailsEntry?.key }
        val activityDetailsBundle = activityDetailsEntry?.value?.mapNotNull { activityDetails ->
            ActivityDetailsUiState.Activity(
                id = activityDetails.id,
                title = activityDetails.title,
                imageUrl = activityDetails.imageUrl,
                latitude = activityDetails.latitude,
                longitude = activityDetails.longitude,
                rating = activityDetails.rating,
                locationId = location?.id ?: return@mapNotNull null,
                locationCity = location.city,
                locationCountry = location.country,
                isFavourite = activityDetailsId in favouriteActivityIds
            )
        }

        val currentActivityDetails = activityDetailsBundle?.firstOrNull {
            it.id == activityDetailsId
        }

        val relatedActivityDetails = activityDetailsBundle?.filter {
            it.id != activityDetailsId
        } ?: emptyList()

        _uiState.update {
            it.copy(
                currentActivity = currentActivityDetails,
                relatedActivities = relatedActivityDetails
            )
        }
    }

    fun changeFavouriteState() = viewModelScope.launch {
        _uiState.update {
            val isFavourite = it.currentActivity?.isFavourite == false

            when {
                isFavourite -> databaseRepository.saveFavouriteLocation(activityDetailsId)
                else -> databaseRepository.removeFavouriteLocation(activityDetailsId)
            }

            it.copy(currentActivity = it.currentActivity?.copy(isFavourite = isFavourite))
        }
    }
}
