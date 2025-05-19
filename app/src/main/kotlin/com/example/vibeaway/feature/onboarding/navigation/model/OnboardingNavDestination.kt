package com.example.vibeaway.feature.onboarding.navigation.model

import kotlinx.serialization.Serializable

/**
 * Sealed class for all possible navigation destination from onboarding flow.
 */
@Serializable
sealed class OnboardingNavDestination {

    @Serializable
    data object Welcome

    @Serializable
    data object ManualInput

    @Serializable
    data class Form(val index: Int)
}
