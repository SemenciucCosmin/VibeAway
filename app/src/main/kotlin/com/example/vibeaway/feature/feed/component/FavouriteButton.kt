package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun FavouriteButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        Image(
            modifier = Modifier.padding(Spacing.Small),
            contentDescription = null,
            painter = when {
                isSelected -> painterResource(R.drawable.ic_heart)
                else -> painterResource(R.drawable.ic_heart_outline)
            }
        )
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFavouriteButtonSelected() {
    VibeAwayTheme {
        FavouriteButton(
            isSelected = true,
            onClick = {},
            modifier = Modifier.size(Spacing.Large)
        )
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewFavouriteButtonUnselected() {
    VibeAwayTheme {
        FavouriteButton(
            isSelected = false,
            onClick = {},
            modifier = Modifier.size(Spacing.Large)
        )
    }
}
