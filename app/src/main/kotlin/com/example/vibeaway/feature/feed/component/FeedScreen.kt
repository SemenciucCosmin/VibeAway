package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.feature.feed.viewmodel.model.FeedUiState
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme
import kotlin.random.Random

private const val COLUMN_COUNT = 2

@Composable
fun FeedScreen(
    locationsDetails: List<FeedUiState.LocationDetails>,
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
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(Spacing.Medium),
            columns = GridCells.Fixed(COLUMN_COUNT),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            item(span = { GridItemSpan(COLUMN_COUNT) }) {
                Text(
                    text = stringResource(R.string.lbl_recommendation_title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item(span = { GridItemSpan(COLUMN_COUNT) }) {
                RecommendedLocationsSection(
                    onLocationsCategoryClick = onLocationsCategoryClick
                )
            }

            item(span = { GridItemSpan(COLUMN_COUNT) }) {
                RecommendedActivitiesSection(
                    onActivitiesCategoryClick = onActivitiesCategoryClick
                )
            }

            item(span = { GridItemSpan(COLUMN_COUNT) }) {
                Spacer(modifier = Modifier.size(Spacing.Medium))
            }

            item(span = { GridItemSpan(COLUMN_COUNT) }) {
                PopularTitleSection(
                    onViewAllClick = onViewAllClick
                )
            }

            items(locationsDetails) { locationsDetails ->
                LocationCard(
                    modifier = Modifier.aspectRatio(0.8f),
                    city = locationsDetails.city,
                    country = locationsDetails.country,
                    imageUrl = locationsDetails.imageUrl,
                    isFavourite = locationsDetails.isFavourite,
                    onClick = { onLocationDetailsClick(locationsDetails.id) },
                    onFavouriteClick = { onLocationDetailsFavouriteClick(locationsDetails.id) }
                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFeedScreen() {
    VibeAwayTheme {
        FeedScreen(
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
                    imageUrl = "https://i.pinimg.com/236x/ed/61/19/ed61199724b1233673a76f5dbb4392c5.jpg",
                )
            }
        )
    }
}
