package com.example.vibeaway.feature.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.domain.core.string.BLANK
import com.example.vibeaway.feature.auth.model.AuthScreenType
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Generic form for authentication, customizable between sing in and sign up
 */
@Composable
fun AuthForm(
    authScreenType: AuthScreenType,
    email: String,
    password: String,
    secondaryPassword: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    isSecondaryPasswordError: Boolean,
    emailErrorLabel: String,
    passwordErrorLabel: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecondaryPasswordChange: (String) -> Unit,
    onChangeAuthClick: () -> Unit,
    onSignClick: () -> Unit,
    onSignWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryPasswordErrorLabel: String = String.BLANK,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(Spacing.Normal)
    ) {
        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            isError = isEmailError,
            supportingText = emailErrorLabel,
            onValueChange = onEmailChange
        )

        Spacer(modifier = Modifier.height(Spacing.Normal))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            isError = isPasswordError,
            supportingText = passwordErrorLabel,
            onValueChange = onPasswordChange
        )

        AnimatedVisibility(visible = authScreenType == AuthScreenType.SIGN_UP) {
            Column {
                Spacer(modifier = Modifier.height(Spacing.Normal))

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = secondaryPassword,
                    isError = isSecondaryPasswordError,
                    supportingText = secondaryPasswordErrorLabel,
                    onValueChange = onSecondaryPasswordChange
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.Normal))

        Button(
            onClick = onSignClick,
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(authScreenType.signLabelRes))
        }

        Spacer(modifier = Modifier.height(Spacing.Normal))

        AuthGoogleButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.lbl_sign_in_google),
            onClick = onSignWithGoogleClick
        )

        Spacer(modifier = Modifier.height(Spacing.Normal))

        TextButton(onClick = onChangeAuthClick) {
            Text(text = stringResource(authScreenType.redirectLabelRes))
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewAuthForm() {
    VibeAwayTheme {
        AuthForm(
            authScreenType = AuthScreenType.SIGN_UP,
            email = "my_email@yahoo.com",
            password = "password123",
            secondaryPassword = "password123",
            isEmailError = false,
            isPasswordError = false,
            isSecondaryPasswordError = false,
            secondaryPasswordErrorLabel = "",
            emailErrorLabel = "",
            passwordErrorLabel = "",
            onEmailChange = {},
            onPasswordChange = {},
            onSecondaryPasswordChange = {},
            onChangeAuthClick = {},
            onSignClick = {},
            onSignWithGoogleClick = {},
        )
    }
}
