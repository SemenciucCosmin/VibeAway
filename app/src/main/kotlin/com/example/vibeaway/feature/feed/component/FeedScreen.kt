package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.feature.feed.viewmodel.model.FeedUiState
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme
import kotlin.random.Random

@Composable
fun FeedScreen(
    locationsDetails: List<FeedUiState.LocationDetails>,
    isLoading: Boolean,
    onLocationsCategoryClick: () -> Unit,
    onActivitiesCategoryClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onLocationDetailsClick: (String) -> Unit,
    onLocationDetailsFavouriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TitleBar(label = stringResource(R.string.lbl_feed_title)) }
    ) { paddingValues ->
        when {
            isLoading -> ProgressOverlay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            else -> FeedContent(
                locationsDetails = locationsDetails,
                onLocationsCategoryClick = onLocationsCategoryClick,
                onActivitiesCategoryClick = onActivitiesCategoryClick,
                onViewAllClick = onViewAllClick,
                onLocationDetailsClick = onLocationDetailsClick,
                onLocationDetailsFavouriteClick = onLocationDetailsFavouriteClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFeedScreen() {
    VibeAwayTheme {
        FeedScreen(
            isLoading = false,
            onLocationsCategoryClick = {},
            onActivitiesCategoryClick = {},
            onViewAllClick = {},
            onLocationDetailsClick = {},
            onLocationDetailsFavouriteClick = {},
            locationsDetails = List(6) {
                FeedUiState.LocationDetails(
                    id = it.toString(),
                    city = "City: $it",
                    country = "Country: $it",
                    isFavourite = Random.nextBoolean(),
                    imageFileName = "https://i.pinimg.com/236x/ed/61/19/ed61199724b1233673a76f5dbb4392c5.jpg",
                )
            }
        )
    }
}
