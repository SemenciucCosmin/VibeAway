package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun LocationCard(
    city: String,
    country: String,
    imageUrl: String?,
    isFavourite: Boolean,
    onClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Radius.Medium))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(Radius.Medium))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(Spacing.Small)
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.XXSmall)) {
                    Text(
                        text = city,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )

                    Text(
                        text = country,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }

                Spacer(modifier = Modifier.size(Spacing.Medium))

                FavouriteButton(
                    isSelected = isFavourite,
                    onClick = onFavouriteClick,
                    modifier = Modifier.size(Spacing.XLarge)
                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewLocationCard() {
    VibeAwayTheme {
        LocationCard(
            city = "Rome",
            country = "Italy",
            imageUrl = "https://i.pinimg.com/236x/ed/61/19/ed61199724b1233673a76f5dbb4392c5.jpg",
            isFavourite = true,
            onClick = {},
            onFavouriteClick = {},
            modifier = Modifier
        )
    }
}
