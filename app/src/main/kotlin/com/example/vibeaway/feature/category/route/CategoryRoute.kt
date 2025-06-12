package com.example.vibeaway.feature.category.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.category.component.CategoryScreen
import com.example.vibeaway.feature.category.model.Category
import com.example.vibeaway.feature.category.viewmodel.CategoryViewModel
import com.example.vibeaway.ui.navigation.model.RecommendationNavDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryRoute(
    categoryId: Int,
    navController: NavController
) {
    val category = Category.getById(categoryId)
    val viewModel: CategoryViewModel = koinViewModel(parameters = { parametersOf(category) })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoryScreen(
        title = stringResource(category.titleRes),
        items = uiState.items,
        isLoading = uiState.isLoading,
        onBack = navController::navigateUp,
        onFavouriteClick = viewModel::changeItemFavouriteState,
        onItemClick = { itemId ->
            val destination = when (category) {
                Category.RECOMMENDED_ACTIVITIES,
                Category.FAVOURITE_ACTIVITIES -> {
                    RecommendationNavDestination.ActivityDetails(itemId)
                }

                else -> {
                    RecommendationNavDestination.LocationDetails(itemId)
                }
            }

            navController.navigate(destination)
        }
    )
}
