package com.example.vibeaway.ui.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.core.modifier.conditional

/**
 * Custom loading composable
 */
@Composable
fun ProgressOverlay(
    modifier: Modifier = Modifier,
    hasBackground: Boolean = false,
    label: String? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)

                Spacer(modifier = Modifier.size(Spacing.Medium))

                label?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
