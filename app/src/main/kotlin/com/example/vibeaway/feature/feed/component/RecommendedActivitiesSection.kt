package com.example.vibeaway.feature.feed.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

@Composable
fun RecommendedActivitiesSection(
    onActivitiesCategoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        title = stringResource(R.string.lbl_activities),
        description = stringResource(R.string.lbl_recommended_activities_message),
        onClick = onActivitiesCategoryClick,
        leadingContent = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .size(Spacing.XXLarge)
            ) {
                Image(
                    painter = painterResource(R.drawable.activity),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.Center)
                )
            }
        }
    )
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewRecommendedActivitiesSection() {
    VibeAwayTheme {
        RecommendedActivitiesSection(
            onActivitiesCategoryClick = {},
            modifier = Modifier.padding(
                vertical = Spacing.Medium,
                horizontal = Spacing.Large
            )
        )
    }
}
