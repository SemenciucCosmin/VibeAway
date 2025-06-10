package com.example.vibeaway.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.components.PrimaryButton
import com.example.vibeaway.ui.catalog.components.TitleBar
import com.example.vibeaway.ui.catalog.dimension.Spacing

@Composable
fun SettingsScreen(
    onSignOutClick: () -> Unit,
    onResetBfiScoresClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TitleBar(label = stringResource(R.string.lbl_settings))
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(Spacing.Large))

            PrimaryButton(
                text = stringResource(R.string.lbl_sign_out),
                onClick = onSignOutClick,
            )

            PrimaryButton(
                text = stringResource(R.string.lbl_reset_score),
                onClick = onResetBfiScoresClick,
            )
        }
    }
}
