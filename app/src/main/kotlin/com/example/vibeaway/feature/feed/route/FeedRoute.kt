package com.example.vibeaway.feature.feed.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
        isLoading = uiState.isLoading,
        onLocationDetailsFavouriteClick = viewModel::changeLocationDetailsFavouriteState,
        onLocationsCategoryClick = {
            navController.navigate(RecommendationNavDestination.Category)
        },
        onActivitiesCategoryClick = {
            navController.navigate(RecommendationNavDestination.Category)
        },
        onLocationDetailsClick = { locationDetailsId ->
            navController.navigate(RecommendationNavDestination.LocationDetails(locationDetailsId))
        },
        onViewAllClick = {
            navController.navigate(RecommendationNavDestination.Category)
        }
    )
}
