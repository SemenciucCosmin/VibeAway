package com.example.vibeaway.feature.locationdetails.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.feature.locationdetails.viewmodel.model.LocationDetailsUiState
import com.example.vibeaway.ui.catalog.components.DetailsScreen
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun LocationDetailsScreen(
    city: String,
    country: String,
    description: String,
    imageFileName: String?,
    isFavourite: Boolean,
    activities: List<LocationDetailsUiState.Activity>,
    isLoading: Boolean,
    onLocationClick: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onActivityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageModel = imageFileName?.let {
        context.resources.getIdentifier(it, "drawable", context.packageName)
    }

    Scaffold(modifier = modifier) { paddingValues ->
        when {
            isLoading -> ProgressOverlay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            else -> DetailsScreen(
                imageModel = imageModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                LocationDetailsContent(
                    city = city,
                    country = country,
                    description = description,
                    isFavourite = isFavourite,
                    activities = activities,
                    onLocationClick = onLocationClick,
                    onActivityClick = onActivityClick,
                    onFavouriteClicked = onFavouriteClicked,
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
private fun PreviewLocationDetailsScreen() {
    VibeAwayTheme {
        LocationDetailsScreen(
            city = "Satu Mare",
            country = "Romania",
            description = "Best city in Romania for all its beautiful locations, especially the cemetery from the city center; must've been a very intelligent man that put it there. <3",
            imageFileName = null,
            isLoading = false,
            isFavourite = true,
            onLocationClick = {},
            onActivityClick = {},
            onFavouriteClicked = {},
            activities = List(4) {
                LocationDetailsUiState.Activity(
                    id = it.toString(),
                    title = "Activity $it",
                    description = "This activity has index $it",
                    imageUrl = null
                )
            }
        )
    }
}
