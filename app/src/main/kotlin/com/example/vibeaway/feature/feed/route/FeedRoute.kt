package com.example.vibeaway.feature.feed.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.category.model.Category
import com.example.vibeaway.feature.feed.component.FeedScreen
import com.example.vibeaway.feature.feed.viewmodel.FeedViewModel
import com.example.vibeaway.ui.navigation.model.RecommendationNavDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedRoute(navController: NavController) {
    val viewModel: FeedViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreen(
        locationsDetails = uiState.locationsDetails,
        isLoading = uiState.isRecommendationsLoading || uiState.isFeedLoading,
        isError = uiState.isError,
        onRetry = viewModel::loadRecommendations,
        onLocationDetailsFavouriteClick = viewModel::changeLocationDetailsFavouriteState,
        onLocationsCategoryClick = {
            val categoryId = Category.RECOMMENDED_LOCATIONS.ordinal
            val destination = RecommendationNavDestination.Category(categoryId)
            navController.navigate(destination)
        },
        onActivitiesCategoryClick = {
            val categoryId = Category.RECOMMENDED_ACTIVITIES.ordinal
            val destination = RecommendationNavDestination.Category(categoryId)
            navController.navigate(destination)
        },
        onViewAllClick = {
            val categoryId = Category.POPULAR_LOCATIONS.ordinal
            val destination = RecommendationNavDestination.Category(categoryId)
            navController.navigate(destination)
        },
        onLocationDetailsClick = { locationDetailsId ->
            val destination = RecommendationNavDestination.LocationDetails(locationDetailsId)
            navController.navigate(destination)
        }
    )
}
