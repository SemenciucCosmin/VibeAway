package com.example.vibeaway.feature.locationdetails.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.vibeaway.feature.feed.component.FavouriteButton
import com.example.vibeaway.feature.locationdetails.viewmodel.model.LocationDetailsUiState
import com.example.vibeaway.ui.catalog.components.ListItem
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun LocationDetailsContent(
    city: String,
    country: String,
    description: String,
    isFavourite: Boolean,
    activities: List<LocationDetailsUiState.Activity>,
    onLocationClick: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onActivityClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = city,
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onLocationClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = null,
            )

            Text(
                text = country,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.size(Spacing.Large))

        Text(
            text = stringResource(R.string.lbl_description_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(Spacing.Medium))

        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.size(Spacing.Large))

        Text(
            text = stringResource(R.string.lbl_activities_for_you_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(Spacing.Medium))

        activities.forEach { activity ->
            ListItem(
                modifier = Modifier.height(IntrinsicSize.Min),
                title = activity.title,
                description = activity.description,
                onClick = { onActivityClick(activity.id) },
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
@Composable
private fun PreviewLocationDetailsContent() {
    VibeAwayTheme {
        LocationDetailsContent(
            city = "Satu Mare",
            country = "Romania",
            description = "Best city in Romania for all its beautiful locations, especially the cemetery from the city center; must've been a very intelligent man that put it there. <3",
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
