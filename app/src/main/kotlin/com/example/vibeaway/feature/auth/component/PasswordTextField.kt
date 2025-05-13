package com.example.vibeaway.feature.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.catalog.dimension.IconSize
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Custom text field for password input
 */
@Composable
fun PasswordTextField(
    value: String,
    isError: Boolean,
    supportingText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        singleLine = true,
        visualTransformation = when {
            isPasswordVisible -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        supportingText = when {
            supportingText.isEmpty() -> null
            else -> {
                { Text(text = supportingText) }
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.lbl_password))
        },
        trailingIcon = {
            Icon(
                contentDescription = when {
                    isPasswordVisible -> {
                        stringResource(R.string.lbl_accessibility_password_visible)
                    }

                    else -> {
                        stringResource(R.string.lbl_accessibility_password_hidden)
                    }
                },
                painter = when {
                    isPasswordVisible -> painterResource(R.drawable.ic_password_visible)
                    else -> painterResource(R.drawable.ic_password_hidden)
                },
                modifier = Modifier
                    .size(IconSize.Small)
                    .clickable { isPasswordVisible = !isPasswordVisible }
            )
        }
    )
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewPasswordTextField() {
    VibeAwayTheme {
        PasswordTextField(
            value = "password123",
            isError = false,
            supportingText = "",
            onValueChange = {},
        )
    }
}
