package com.example.vibeaway.feature.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.data.repository.RecommendationRepository
import com.example.vibeaway.domain.core.string.BLANK
import com.example.vibeaway.domain.locationdetails.usecase.GetPopularLocationDetailsUseCase
import com.example.vibeaway.feature.category.model.Category
import com.example.vibeaway.feature.category.viewmodel.model.CategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val category: Category,
    private val databaseRepository: DatabaseRepository,
    private val getPopularLocationDetailsUseCase: GetPopularLocationDetailsUseCase,
    private val recommendationRepository: RecommendationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()
        .onStart { loadItems() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    private fun loadItems() {
        _uiState.update { it.copy(isLoading = true) }

        when (category) {
            Category.RECOMMENDED_LOCATIONS -> loadRecommendedLocations()
            Category.RECOMMENDED_ACTIVITIES -> loadRecommendedActivities()
            Category.POPULAR_LOCATIONS -> loadPopularLocations()
            Category.FAVOURITE_LOCATIONS -> loadFavouriteLocations()
            Category.FAVOURITE_ACTIVITIES -> loadFavouriteActivities()
        }
    }

    private fun loadRecommendedLocations() = viewModelScope.launch {
        val favouriteLocationsIds = databaseRepository.getFavouriteLocationIds()
        val items = recommendationRepository.getRecommendedLocationsDetails().map {
            CategoryUiState.Item(
                id = it.id,
                title = it.city,
                label = it.country,
                imageUrl = null,
                imageFileName = it.imageFileName,
                isFavourite = favouriteLocationsIds.contains(it.id)
            )
        }

        _uiState.update {
            it.copy(
                items = items,
                isLoading = false,
            )
        }
    }

    private fun loadRecommendedActivities() = viewModelScope.launch {
        val favouriteLocationsIds = databaseRepository.getFavouriteActivityIds()
        val items = recommendationRepository.getRecommendedActivitiesDetails().map {
            CategoryUiState.Item(
                id = it.id,
                title = it.title,
                label = String.BLANK,
                imageUrl = it.imageUrl,
                imageFileName = null,
                isFavourite = favouriteLocationsIds.contains(it.id)
            )
        }

        _uiState.update {
            it.copy(
                items = items,
                isLoading = false,
            )
        }
    }

    private fun loadPopularLocations() = viewModelScope.launch {
        val favouriteLocationsIds = databaseRepository.getFavouriteLocationIds()
        val items = getPopularLocationDetailsUseCase().map {
            CategoryUiState.Item(
                id = it.id,
                title = it.city,
                label = it.country,
                imageUrl = null,
                imageFileName = it.imageFileName,
                isFavourite = favouriteLocationsIds.contains(it.id)
            )
        }

        _uiState.update {
            it.copy(
                items = items,
                isLoading = false,
            )
        }
    }

    private fun loadFavouriteLocations() = viewModelScope.launch {
        val favouriteLocationsIds = databaseRepository.getFavouriteLocationIds()
        val items = recommendationRepository.getRecommendedLocationsDetails().map {
            CategoryUiState.Item(
                id = it.id,
                title = it.city,
                label = it.country,
                imageUrl = null,
                imageFileName = it.imageFileName,
                isFavourite = favouriteLocationsIds.contains(it.id)
            )
        }.filter { it.isFavourite }

        _uiState.update {
            it.copy(
                items = items,
                isLoading = false,
            )
        }
    }

    private fun loadFavouriteActivities() = viewModelScope.launch {
        val favouriteLocationsIds = databaseRepository.getFavouriteActivityIds()
        val items = recommendationRepository.getRecommendedActivitiesDetails().map {
            CategoryUiState.Item(
                id = it.id,
                title = it.title,
                label = String.BLANK,
                imageUrl = it.imageUrl,
                imageFileName = null,
                isFavourite = favouriteLocationsIds.contains(it.id)
            )
        }.filter { it.isFavourite }

        _uiState.update {
            it.copy(
                items = items,
                isLoading = false,
            )
        }
    }

    fun changeItemFavouriteState(itemId: String) = viewModelScope.launch {
        _uiState.update {
            val updatedItems = it.items.map { item ->
                when {
                    item.id == itemId && item.isFavourite -> {
                        when (category) {
                            Category.RECOMMENDED_ACTIVITIES,
                            Category.FAVOURITE_ACTIVITIES -> {
                                databaseRepository.removeFavouriteActivity(itemId)
                            }

                            else -> {
                                databaseRepository.removeFavouriteLocation(itemId)
                            }
                        }

                        item.copy(isFavourite = false)
                    }

                    item.id == itemId -> {
                        when (category) {
                            Category.RECOMMENDED_ACTIVITIES,
                            Category.FAVOURITE_ACTIVITIES -> {
                                databaseRepository.saveFavouriteActivity(itemId)
                            }

                            else -> {
                                databaseRepository.saveFavouriteLocation(itemId)
                            }
                        }

                        item.copy(isFavourite = true)
                    }

                    else -> item
                }
            }

            it.copy(items = updatedItems)
        }
    }
}
