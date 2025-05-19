package com.example.vibeaway.feature.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.components.PrimaryButton
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Starting screen for onboarding flow
 */
@Composable
fun WelcomeScreen(
    onWriteManualClick: () -> Unit,
    onLetsGoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.onboarding_cover),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(Spacing.XLarge))

            Column(modifier = Modifier.padding(horizontal = Spacing.Large)) {
                Text(
                    text = stringResource(R.string.lbl_onboarding_title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(Spacing.Medium))

                Text(
                    text = stringResource(R.string.lbl_onboarding_message),
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.lbl_onboarding_redirect_message),
                        style = MaterialTheme.typography.bodySmall
                    )

                    TextButton(onClick = onWriteManualClick) {
                        Text(
                            text = stringResource(R.string.lbl_onboarding_redirect_action),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spacing.Medium))

                PrimaryButton(
                    text = stringResource(R.string.lbl_onboarding_lets_go),
                    onClick = onLetsGoClick
                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewWelcomeScreen() {
    VibeAwayTheme {
        WelcomeScreen(
            onWriteManualClick = {},
            onLetsGoClick = {}
        )
    }
}
