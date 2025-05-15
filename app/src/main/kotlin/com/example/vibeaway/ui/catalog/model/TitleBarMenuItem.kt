package com.example.vibeaway.ui.catalog.model

data class TitleBarMenuItem(
    val action: () -> Unit,
    val label: String? = null,
    val iconResId: Int? = null,
    val contentDescription: String? = null,
    val enabled: Boolean = true,
)
