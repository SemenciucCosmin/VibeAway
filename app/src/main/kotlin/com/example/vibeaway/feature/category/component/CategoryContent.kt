package com.example.vibeaway.feature.category.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.feature.category.viewmodel.model.CategoryUiState
import com.example.vibeaway.ui.catalog.components.ListItemCard
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme
import kotlin.random.Random

@Composable
fun CategoryContent(
    items: List<CategoryUiState.Item>,
    onItemClick: (String) -> Unit,
    onFavouriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Spacing.Medium),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        items(items) { item ->
            val imageModel = when {
                item.imageFileName != null -> {
                    context.resources.getIdentifier(
                        item.imageFileName,
                        "drawable",
                        context.packageName
                    )
                }

                else -> item.imageUrl
            }

            ListItemCard(
                title = item.title,
                label = item.label,
                imageModel = imageModel,
                isFavourite = item.isFavourite,
                onClick = { onItemClick(item.id) },
                onFavouriteClick = { onFavouriteClick(item.id) },
                modifier = Modifier.aspectRatio(1.5f)
            )
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewCategoryContent() {
    VibeAwayTheme {
        CategoryContent(
            onItemClick = {},
            onFavouriteClick = {},
            items = List(6) {
                CategoryUiState.Item(
                    id = it.toString(),
                    title = "Title: $it",
                    label = "label: $it",
                    isFavourite = Random.nextBoolean(),
                    imageUrl = "https://i.pinimg.com/236x/ed/61/19/ed61199724b1233673a76f5dbb4392c5.jpg",
                    imageFileName = null,
                )
            }
        )
    }
}
