package com.example.vibeaway.feature.category.viewmodel.model

data class CategoryUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
) {
    data class Item(
        val id: String,
        val title: String,
        val label: String,
        val imageUrl: String?,
        val isFavourite: Boolean
    )
}
