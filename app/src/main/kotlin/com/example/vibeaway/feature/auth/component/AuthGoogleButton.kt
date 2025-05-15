package com.example.vibeaway.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.IconSize
import com.example.vibeaway.ui.catalog.dimension.Radius
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
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Radius.Large),
        contentPadding = PaddingValues(Spacing.Medium),
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
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
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    )
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
