package com.example.vibeaway.feature.auth.component

import EmailError
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.vibeaway.R
import com.example.vibeaway.feature.auth.model.AuthScreenType
import com.example.vibeaway.feature.auth.model.PasswordError
import com.example.vibeaway.ui.catalog.components.ProgressOverlay
import com.example.vibeaway.ui.catalog.dimension.Spacing
import com.example.vibeaway.ui.catalog.preview.PreviewVibeAway
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Screen for sign in and sign up flows
 */
@Composable
fun AuthScreen(
    authScreenType: AuthScreenType,
    isLoading: Boolean,
    email: String,
    password: String,
    secondaryPassword: String,
    emailError: EmailError,
    passwordError: PasswordError,
    secondaryPasswordError: PasswordError,
    snackbarHostState: SnackbarHostState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSecondaryPasswordChange: (String) -> Unit,
    onChangeAuthClick: () -> Unit,
    onSignClick: () -> Unit,
    onSignWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        when {
            isLoading -> ProgressOverlay(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

            else -> Column(modifier = Modifier.padding(paddingValues)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(Spacing.Normal)
                ) {
                    Text(
                        text = stringResource(R.string.lbl_app_name),
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                AuthForm(
                    authScreenType = authScreenType,
                    email = email,
                    password = password,
                    secondaryPassword = secondaryPassword,
                    isEmailError = emailError != EmailError.NONE,
                    isPasswordError = passwordError != PasswordError.NONE,
                    isSecondaryPasswordError = secondaryPasswordError != PasswordError.NONE,
                    emailErrorLabel = stringResource(emailError.label),
                    passwordErrorLabel = stringResource(passwordError.label),
                    secondaryPasswordErrorLabel = stringResource(secondaryPasswordError.label),
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onSecondaryPasswordChange = onSecondaryPasswordChange,
                    onChangeAuthClick = onChangeAuthClick,
                    onSignClick = onSignClick,
                    onSignWithGoogleClick = onSignWithGoogleClick,
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                        .padding(Spacing.Normal)

                )
            }
        }
    }
}

@Preview
@PreviewVibeAway
@Composable
private fun PreviewAuthScreen() {
    VibeAwayTheme {
        AuthScreen(
            authScreenType = AuthScreenType.SIGN_UP,
            email = "my_email@yahoo.com",
            password = "password123",
            secondaryPassword = "password123",
            isLoading = false,
            emailError = EmailError.NONE,
            passwordError = PasswordError.NONE,
            secondaryPasswordError = PasswordError.NONE,
            snackbarHostState = SnackbarHostState(),
            onEmailChange = {},
            onPasswordChange = {},
            onSecondaryPasswordChange = {},
            onChangeAuthClick = {},
            onSignClick = {},
            onSignWithGoogleClick = {}
        )
    }
}
