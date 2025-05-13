package com.example.vibeaway.feature.auth.route

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vibeaway.feature.auth.component.AuthScreen
import com.example.vibeaway.feature.auth.viewmodel.AuthViewModel
import com.example.vibeaway.feature.auth.viewmodel.model.AuthUiState
import com.example.vibeaway.ui.core.components.EventHandler
import org.koin.androidx.compose.koinViewModel

/**
 * Route for authentication feature
 */
@Composable
fun AuthRoute(onSigned: () -> Unit) {
    val viewModel = koinViewModel<AuthViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AuthScreen(
        modifier = Modifier.imePadding(),
        authScreenType = uiState.authScreenType,
        isLoading = uiState.isLoading,
        email = uiState.email,
        password = uiState.password,
        secondaryPassword = uiState.secondaryPassword,
        emailError = uiState.emailError,
        passwordError = uiState.passwordError,
        secondaryPasswordError = uiState.secondaryPasswordError,
        snackbarHostState = viewModel.snackbarHostState,
        onEmailChange = viewModel::changeEmail,
        onPasswordChange = viewModel::changePassword,
        onSecondaryPasswordChange = viewModel::changeSecondaryPassword,
        onChangeAuthClick = viewModel::changeAuth,
        onSignClick = viewModel::sign,
        onSignWithGoogleClick = viewModel::signWithGoogle,
    )

    EventHandler(viewModel.events) { event ->
        when (event) {
            AuthUiState.Event.SIGNED -> {
                onSigned()
                viewModel.unregisterEvent(event)
            }
        }
    }
}
