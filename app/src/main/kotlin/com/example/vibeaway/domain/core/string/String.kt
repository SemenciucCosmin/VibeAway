package com.example.vibeaway.domain.core.string

import com.example.vibeaway.domain.core.pattern.Pattern

val String.Companion.BLANK: String
    get() = ""

val String.Companion.ASTERISK: String
    get() = "*"

val String.Companion.QUESTION: String
    get() = "?"

fun String.isValidEmail() = when {
    this.isEmpty() -> false
    else -> Pattern.EMAIL_ADDRESS.toRegex().matches(this)
}

fun String.isValidPassword() = when {
    this.isEmpty() -> false
    else -> Pattern.PASSWORD.toRegex().matches(this)
}