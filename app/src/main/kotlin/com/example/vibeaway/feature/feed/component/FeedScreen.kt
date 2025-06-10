package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.feature.feed.viewmodel.model.FeedUiState
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme
import kotlin.random.Random

@Composable
fun FeedScreen(
    locationsDetails: List<FeedUiState.LocationDetails>,
    isLoading: Boolean,
    isError: Boolean,
    onLocationsCategoryClick: () -> Unit,
    onActivitiesCategoryClick: () -> Unit,
    onViewAllClick: () -> Unit,
    onLocationDetailsClick: (String) -> Unit,
    onLocationDetailsFavouriteClick: (String) -> Unit,
    onRetry: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleBar(
                label = stringResource(R.string.lbl_feed_title),
                actionIcon = painterResource(R.drawable.ic_settings),
                onAction = onSettingsClick
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> ProgressOverlay(
                label = stringResource(R.string.lbl_recommendations_loading),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            isError -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    Spacing.Medium,
                    Alignment.CenterVertically
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = stringResource(R.string.lbl_recommendations_error),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    modifier = Modifier,
                    onClick = onRetry,
                    shape = RoundedCornerShape(Radius.Large),
                    contentPadding = PaddingValues(Spacing.Medium),
                    content = {
                        Text(
                            text = stringResource(R.string.lbl_retry),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
            }

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
            isError = false,
            onLocationsCategoryClick = {},
            onActivitiesCategoryClick = {},
            onViewAllClick = {},
            onLocationDetailsClick = {},
            onLocationDetailsFavouriteClick = {},
            onRetry = {},
            onSettingsClick = {},
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
