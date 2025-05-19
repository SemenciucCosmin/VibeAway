package com.example.vibeaway.feature.auth.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewModelScope
import com.example.vibeaway.data.auth.datasource.GoogleCredentialDataSource
import com.example.vibeaway.data.auth.repository.AuthRepository
import com.example.vibeaway.domain.core.string.BLANK
import com.example.vibeaway.domain.core.string.isValidEmail
import com.example.vibeaway.domain.core.string.isValidPassword
import com.example.vibeaway.feature.auth.model.AuthScreenType
import com.example.vibeaway.feature.auth.model.PasswordError
import com.example.vibeaway.feature.auth.viewmodel.model.AuthUiState
import com.example.vibeaway.ui.core.viewmodel.EventViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View model for auth flow
 */
class AuthViewModel(
    private val authRepository: AuthRepository,
    private val googleCredentialDataSource: GoogleCredentialDataSource
) : EventViewModel<AuthUiState.Event>() {

    val snackbarHostState = SnackbarHostState()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
        .onStart {
            _uiState.update { it.copy(isLoading = true) }
            listenToAuthSignState()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = _uiState.value
        )

    private fun listenToAuthSignState() = viewModelScope.launch {
        authRepository.getCurrentUserFlow().collectLatest { user ->
            when (user) {
                null -> _uiState.update { it.copy(isLoading = false) }
                else -> registerEvent(AuthUiState.Event.SIGNED)
            }
        }
    }

    fun changeEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = EmailError.NONE
            )
        }
    }

    fun changePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = PasswordError.NONE
            )
        }
    }

    fun changeSecondaryPassword(secondaryPassword: String) {
        _uiState.update {
            it.copy(
                secondaryPassword = secondaryPassword,
                secondaryPasswordError = PasswordError.NONE
            )
        }
    }

    fun changeAuth() {
        _uiState.update {
            when (it.authScreenType) {
                AuthScreenType.SIGN_IN -> it.copy(
                    authScreenType = AuthScreenType.SIGN_UP,
                    secondaryPassword = String.BLANK,
                    emailError = EmailError.NONE,
                    passwordError = PasswordError.NONE,
                    secondaryPasswordError = PasswordError.NONE,
                )

                AuthScreenType.SIGN_UP -> it.copy(
                    authScreenType = AuthScreenType.SIGN_IN,
                    emailError = EmailError.NONE,
                    passwordError = PasswordError.NONE,
                    secondaryPasswordError = PasswordError.NONE,
                )
            }
        }
    }

    fun sign() {
        when (_uiState.value.authScreenType) {
            AuthScreenType.SIGN_UP -> {
                validateSignUpData()

                val isAnyError = listOf(
                    _uiState.value.emailError != EmailError.NONE,
                    _uiState.value.passwordError != PasswordError.NONE,
                    _uiState.value.secondaryPasswordError != PasswordError.NONE,
                ).any { it }

                if (!isAnyError) {
                    signUp()
                }
            }

            AuthScreenType.SIGN_IN -> {
                validateSignInData()

                val isAnyError = listOf(
                    _uiState.value.emailError != EmailError.NONE,
                    _uiState.value.passwordError != PasswordError.NONE,
                ).any { it }

                if (!isAnyError) {
                    signIn()
                }
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        runCatching {
            authRepository.signUp(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
        }.onFailure { exception ->
            _uiState.update { it.copy(isLoading = false) }

            if (exception is FirebaseAuthException) {
                handleFirebaseAuthException(exception)
            }
        }
    }

    private fun signIn() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        runCatching {
            authRepository.signIn(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
        }.onFailure { exception ->
            _uiState.update { it.copy(isLoading = false) }

            if (exception is FirebaseAuthException) {
                handleFirebaseAuthException(exception)
            }
        }
    }

    private fun validateSignUpData() {
        _uiState.update { state ->
            val emailError = when {
                state.email.isEmpty() -> EmailError.EMPTY
                !state.email.isValidEmail() -> EmailError.INVALID
                else -> EmailError.NONE
            }

            val passwordError = when {
                state.password.isEmpty() -> PasswordError.EMPTY
                !state.password.isValidPassword() -> PasswordError.INVALID
                state.password != state.secondaryPassword -> PasswordError.NOT_MATCHING
                else -> PasswordError.NONE
            }

            val secondaryPasswordError = when {
                state.secondaryPassword.isEmpty() -> PasswordError.EMPTY
                !state.secondaryPassword.isValidPassword() -> PasswordError.INVALID
                state.password != state.secondaryPassword -> PasswordError.NOT_MATCHING
                else -> PasswordError.NONE
            }

            state.copy(
                emailError = emailError,
                passwordError = passwordError,
                secondaryPasswordError = secondaryPasswordError
            )
        }
    }

    private fun validateSignInData() {
        _uiState.update { state ->
            val emailError = when {
                state.email.isEmpty() -> EmailError.EMPTY
                !state.email.isValidEmail() -> EmailError.INVALID
                else -> EmailError.NONE
            }

            val passwordError = when {
                state.password.isEmpty() -> PasswordError.EMPTY
                !state.password.isValidPassword() -> PasswordError.INVALID
                else -> PasswordError.NONE
            }

            state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }
    }

    fun signWithGoogle() = viewModelScope.launch {
        val credential = googleCredentialDataSource.getCredential()
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            _uiState.update { it.copy(isLoading = true) }
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            authRepository.signInWithGoogle(googleIdTokenCredential.idToken)
        } else {
            snackbarHostState.showSnackbar("Sign in error. Try again.")
        }
    }

    private fun handleFirebaseAuthException(exception: FirebaseAuthException) {
        when (exception) {
            is FirebaseAuthWeakPasswordException -> {
                _uiState.update {
                    it.copy(passwordError = PasswordError.WEAK)
                }
            }

            is FirebaseAuthUserCollisionException -> {
                _uiState.update {
                    it.copy(emailError = EmailError.COLLISION)
                }
            }

            is FirebaseAuthInvalidCredentialsException -> viewModelScope.launch {
                snackbarHostState.showSnackbar(exception.message ?: String.BLANK)
            }

            else -> viewModelScope.launch {
                snackbarHostState.showSnackbar(exception.message ?: String.BLANK)
            }
        }
    }
}
