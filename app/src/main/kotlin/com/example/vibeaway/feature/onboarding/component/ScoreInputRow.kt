package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.domain.core.string.COLON
import com.example.vibeaway.domain.core.string.SLASH
import com.example.vibeaway.ui.catalog.components.NumberPicker
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

private const val MAXIMUM_SCORE_VALUE = 50
private const val MINIMUM_SCORE_VALUE = 0

/**
 * Row with text field for BFI score manual input
 */
@Composable
fun ScoreInputRow(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = Spacing.Medium)
    ) {
        Text(
            text = label + String.COLON,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(Spacing.XSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                value = value,
                onValueChange = onValueChange,
                dividersColor = MaterialTheme.colorScheme.onPrimary,
                range = MINIMUM_SCORE_VALUE..MAXIMUM_SCORE_VALUE,
                textStyle = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = String.SLASH,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = MAXIMUM_SCORE_VALUE.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewScoreInputRow() {
    VibeAwayTheme {
        ScoreInputRow(
            label = "Input",
            value = 42,
            onValueChange = {},
        )
    }
}
