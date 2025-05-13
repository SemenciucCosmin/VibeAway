package com.example.vibeaway.feature.auth.viewmodel.model

import EmailError
import com.example.vibeaway.domain.core.string.BLANK
import com.example.vibeaway.feature.auth.model.AuthScreenType
import com.example.vibeaway.feature.auth.model.PasswordError
import com.example.vibeaway.ui.core.viewmodel.model.BasicEvent

data class AuthUiState(
    val authScreenType: AuthScreenType = AuthScreenType.SIGN_UP,
    val email: String = String.BLANK,
    val password: String = String.BLANK,
    val secondaryPassword: String = String.BLANK,
    val emailError: EmailError = EmailError.NONE,
    val passwordError: PasswordError = PasswordError.NONE,
    val secondaryPasswordError: PasswordError = PasswordError.NONE,
    val isLoading: Boolean = false
) {
    enum class Event : BasicEvent {
        SIGNED
    }
}
