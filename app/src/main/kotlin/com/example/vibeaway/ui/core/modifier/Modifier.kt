package com.example.vibeaway.ui.core.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Modifier extension for applying a modifier under a certain condition.
 */
@Composable
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier
): Modifier {
    return when {
        condition -> then(modifier(Modifier))
        else -> this
    }
}
