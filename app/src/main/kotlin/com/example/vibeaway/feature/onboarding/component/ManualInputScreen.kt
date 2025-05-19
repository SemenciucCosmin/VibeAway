package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.domain.core.string.BLANK
import com.example.vibeaway.feature.onboarding.model.BFIDimension
import com.example.vibeaway.ui.catalog.components.PrimaryButton
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Screen of manual input in onboarding flow
 */
@Composable
fun ManualInputScreen(
    bfiDimensionsScores: Map<BFIDimension, Int>,
    onScoreChange: (BFIDimension, Int) -> Unit,
    onLetsGoClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleBar(
                label = String.BLANK,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                text = stringResource(R.string.lbl_onboarding_manual_input_title),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(
                    start = Spacing.Large,
                    end = Spacing.Medium
                )
            )

            Spacer(modifier = Modifier.weight(0.1f))

            bfiDimensionsScores.forEach { (dimension, score) ->
                ScoreInputRow(
                    label = stringResource(dimension.labelRes),
                    value = score,
                    onValueChange = { onScoreChange(dimension, it) },
                    modifier = Modifier.padding(
                        start = Spacing.Large,
                        end = Spacing.XXLarge
                    )
                )
            }

            Spacer(modifier = Modifier.weight(0.7f))

            PrimaryButton(
                text = stringResource(R.string.lbl_onboarding_lets_go),
                onClick = onLetsGoClick,
                modifier = Modifier.padding(
                    start = Spacing.Large,
                    end = Spacing.Medium
                )
            )

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewManualInputScreen() {
    VibeAwayTheme {
        ManualInputScreen(
            onBack = {},
            onLetsGoClick = {},
            onScoreChange = { _, _ -> },
            bfiDimensionsScores = BFIDimension.entries.associateWith { 0 }
        )
    }
}
