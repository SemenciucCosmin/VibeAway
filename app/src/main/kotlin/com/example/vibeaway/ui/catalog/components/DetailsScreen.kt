package com.example.vibeaway.ui.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun DetailsScreen(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(-Spacing.Large)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.image_placeholder),
            fallback = painterResource(R.drawable.image_placeholder),
            error = painterResource(R.drawable.image_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
        )

        Surface(
            shape = RoundedCornerShape(Radius.Large),
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewDetailsScreen() {
    VibeAwayTheme {
        DetailsScreen(
            imageUrl = null,
            content = {},
        )
    }
}
