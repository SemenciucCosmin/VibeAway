package com.example.vibeaway.feature.auth.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.dimension.Radius
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Custom text field for email input
 */
@Composable
fun EmailTextField(
    value: String,
    isError: Boolean,
    supportingText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(Radius.Large),
        supportingText = when {
            supportingText.isEmpty() -> null
            else -> {
                { Text(text = supportingText) }
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.lbl_email))
        }
    )
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewEmailTextField() {
    VibeAwayTheme {
        EmailTextField(
            value = "my_email@yahoo.com",
            isError = false,
            supportingText = "",
            onValueChange = {},
        )
    }
}
