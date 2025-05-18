package com.example.vibeaway.feature.onboarding.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vibeaway.feature.onboarding.component.WelcomeScreen
import com.example.vibeaway.feature.onboarding.navigation.model.OnboardingNavDestination

private const val FIRST_FORM_PAGE_INDEX = 0

@Composable
fun WelcomeRoute(navController: NavController) {
    WelcomeScreen(
        onWriteManualClick = {
            navController.navigate(OnboardingNavDestination.ManualInput)
        },
        onLetsGoClick = {
            navController.navigate(OnboardingNavDestination.Form(FIRST_FORM_PAGE_INDEX))
        },
    )
}
