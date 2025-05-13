package com.example.vibeaway.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.IconSize
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Custom button for google authentication.
 */
@Composable
fun AuthGoogleButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                tint = Color.Unspecified,
                modifier = Modifier.size(IconSize.Small),
                painter = painterResource(R.drawable.ic_google),
                contentDescription = null
            )

            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewAuthGoogleButton() {
    VibeAwayTheme {
        AuthGoogleButton(
            label = "Sign in with Google",
            onClick = {},
        )
    }
}
