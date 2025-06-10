package com.example.vibeaway.feature.category.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.vibeaway.feature.category.viewmodel.model.CategoryUiState
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme
import kotlin.random.Random

@Composable
fun CategoryScreen(
    title: String,
    items: List<CategoryUiState.Item>,
    isLoading: Boolean,
    onItemClick: (String) -> Unit,
    onFavouriteClick: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleBar(
                label = title,
                actionIcon = painterResource(R.drawable.ic_arrow_left_large),
                onAction = onBack
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> ProgressOverlay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            items.isEmpty() -> Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = stringResource(R.string.lbl_no_results, title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            else -> CategoryContent(
                items = items,
                onItemClick = onItemClick,
                onFavouriteClick = onFavouriteClick,
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
private fun PreviewCategoryScreen() {
    VibeAwayTheme {
        CategoryScreen(
            title = "Items",
            isLoading = false,
            onBack = {},
            onItemClick = {},
            onFavouriteClick = {},
            items = List(6) {
                CategoryUiState.Item(
                    id = it.toString(),
                    title = "Title: $it",
                    label = "label: $it",
                    isFavourite = Random.nextBoolean(),
                    imageUrl = "https://i.pinimg.com/236x/ed/61/19/ed61199724b1233673a76f5dbb4392c5.jpg",
                    imageFileName = null
                )
            }
        )
    }
}
