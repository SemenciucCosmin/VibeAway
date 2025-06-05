package com.example.vibeaway.feature.activitydetails.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.feature.activitydetails.viewmodel.model.ActivityDetailsUiState
import com.example.vibeaway.ui.catalog.components.DetailsScreen
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun ActivityDetailsScreen(
    activity: ActivityDetailsUiState.Activity?,
    relatedActivities: List<ActivityDetailsUiState.Activity>,
    onLocationClick: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onLocationDetailsClick: () -> Unit,
    onActivityDetailsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        when {
            activity == null -> ProgressOverlay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            else -> DetailsScreen(
                imageModel = activity.imageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                ActivityDetailsContent(
                    city = activity.locationCity,
                    country = activity.locationCountry,
                    isFavourite = activity.isFavourite,
                    title = activity.title,
                    rating = activity.rating,
                    relatedActivities = relatedActivities,
                    onLocationClick = onLocationClick,
                    onFavouriteClicked = onFavouriteClicked,
                    onLocationDetailsClick = onLocationDetailsClick,
                    onActivityDetailsClick = onActivityDetailsClick,
                    modifier = Modifier.padding(
                        vertical = Spacing.XLarge,
                        horizontal = Spacing.Large
                    )
                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewActivityDetailsScreen() {
    VibeAwayTheme {
        ActivityDetailsScreen(
            activity = ActivityDetailsUiState.Activity(
                title = "Moose",
                rating = 4.6,
                locationCity = "Satu Mare",
                locationCountry = "Romania",
                locationId = "",
                isFavourite = true,
                imageUrl = null,
                latitude = 0.0,
                longitude = 0.0,
                id = "",
            ),
            onLocationClick = {},
            onFavouriteClicked = {},
            onActivityDetailsClick = {},
            onLocationDetailsClick = {},
            relatedActivities = List(4) {
                ActivityDetailsUiState.Activity(
                    id = it.toString(),
                    title = "Activity $it",
                    imageUrl = null,
                    latitude = 0.0,
                    longitude = 0.0,
                    rating = 4.6,
                    locationId = it.toString(),
                    locationCity = "Satu Mare",
                    locationCountry = "Romania",
                    isFavourite = true,
                )
            }
        )
    }
}
