package com.example.vibeaway.feature.auth.model

import androidx.annotation.StringRes
import com.example.vibeaway.R

/**
 * Enum class for sign in and sign up authentication flow types.
 */
enum class AuthScreenType(
    @StringRes val signLabelRes: Int,
    @StringRes val messageLabelRes: Int,
    @StringRes val redirectLabelRes: Int,
) {
    SIGN_IN(
        signLabelRes = R.string.lbl_sign_in,
        messageLabelRes = R.string.lbl_sign_in_message,
        redirectLabelRes = R.string.lbl_sign_up_redirect_message,
    ),

    SIGN_UP(
        signLabelRes = R.string.lbl_sign_up,
        messageLabelRes = R.string.lbl_sign_up_message,
        redirectLabelRes = R.string.lbl_sign_in_redirect_message,
    )
}
