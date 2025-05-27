package com.example.vibeaway.ui.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Generic custom primary button
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Radius.Large),
        enabled = enabled,
        contentPadding = PaddingValues(
            vertical = Spacing.Medium,
            horizontal = Spacing.XXLarge
        ),
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_large),
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewPrimaryButton() {
    VibeAwayTheme {
        PrimaryButton(
            text = "Button",
            onClick = {}
        )
    }
}
