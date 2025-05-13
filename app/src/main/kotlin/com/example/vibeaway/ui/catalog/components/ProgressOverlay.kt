package com.example.vibeaway.ui.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vibeaway.ui.core.modifier.conditional

@Composable
fun ProgressOverlay(modifier: Modifier = Modifier, hasBackground: Boolean = false) {
    Box(
        contentAlignment = Alignment.Center,
        content = {
            CircularProgressIndicator(
                strokeWidth = 2.dp
            )
        },
        modifier = modifier.conditional(hasBackground) {
            background(
                MaterialTheme.colorScheme.background.copy(
                    alpha = ProgressOverlayDefaults.ALPHA
                )
            )
        }
    )
}

private object ProgressOverlayDefaults {
    const val ALPHA = 0.7f
}
