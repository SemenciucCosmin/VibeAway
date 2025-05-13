package com.example.vibeaway.feature.auth.model

import androidx.annotation.StringRes
import com.example.vibeaway.R

/**
 * Enum class for all possible states of the password text field.
 */
enum class PasswordError(@StringRes val label: Int) {
    EMPTY(R.string.lbl_empty_password),
    INVALID(R.string.lbl_invalid_password),
    NOT_MATCHING(R.string.lbl_passwords_not_matching),
    WEAK(R.string.lbl_weak_password),
    NONE(R.string.lbl_empty)
}
