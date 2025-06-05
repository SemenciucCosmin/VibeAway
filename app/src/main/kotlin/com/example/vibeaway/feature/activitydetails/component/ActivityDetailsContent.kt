package com.example.vibeaway.feature.activitydetails.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vibeaway.R
import com.example.vibeaway.feature.activitydetails.viewmodel.model.ActivityDetailsUiState
import com.example.vibeaway.feature.feed.component.FavouriteButton
import com.example.vibeaway.ui.catalog.components.ListItem
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun ActivityDetailsContent(
    title: String,
    city: String,
    country: String,
    rating: Double,
    isFavourite: Boolean,
    relatedActivities: List<ActivityDetailsUiState.Activity>,
    onLocationClick: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onLocationDetailsClick: () -> Unit,
    onActivityDetailsClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            FavouriteButton(
                isSelected = isFavourite,
                onClick = onFavouriteClicked,
                modifier = Modifier.size(Spacing.XLarge)
            )
        }

        Spacer(modifier = Modifier.size(Spacing.XSmall))

        Text(
            text = "$country, $city",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLocationDetailsClick() }
        )

        Spacer(modifier = Modifier.size(Spacing.XSmall))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onLocationClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )

            Spacer(modifier = Modifier.size(Spacing.Small))

            Text(
                text = "$title, $rating",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(Spacing.XXSmall))

            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(12.dp)
            )
        }

        Spacer(modifier = Modifier.size(Spacing.Large))

        if (relatedActivities.isNotEmpty()) {
            Text(
                text = stringResource(R.string.lbl_activities_for_you_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(Spacing.Medium))
        }

        relatedActivities.forEach { activity ->
            ListItem(
                modifier = Modifier.height(IntrinsicSize.Min),
                title = activity.title,
                description = "$city, ${activity.rating}",
                onClick = { onActivityDetailsClick(activity.id) },
                leadingContent = {
                    AsyncImage(
                        model = activity.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.image_placeholder),
                        fallback = painterResource(R.drawable.image_placeholder),
                        error = painterResource(R.drawable.image_placeholder),
                        modifier = Modifier
                            .size(64.dp, 80.dp)
                            .clip(RoundedCornerShape(Radius.Small))
                    )
                }
            )

            Spacer(modifier = Modifier.size(Spacing.Medium))
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewActivityDetailsContent() {
    VibeAwayTheme {
        ActivityDetailsContent(
            title = "Moose",
            rating = 4.6,
            city = "Satu Mare",
            country = "Romania",
            isFavourite = true,
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
