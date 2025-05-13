package com.example.vibeaway.domain.core.pattern

/**
 * Object containing constant values of string patters (regex, date formats etc.)
 */
object Pattern {
    const val EMAIL_ADDRESS = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    const val PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
}
