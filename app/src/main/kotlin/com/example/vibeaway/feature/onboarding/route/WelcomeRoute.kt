package com.example.vibeaway.feature.onboarding.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vibeaway.feature.onboarding.component.WelcomeScreen
import com.example.vibeaway.feature.onboarding.di.FormScope
import com.example.vibeaway.feature.onboarding.navigation.model.OnboardingNavDestination
import org.koin.compose.getKoin

private const val FIRST_FORM_PAGE_INDEX = 0

/**
 * Route for star of onboarding flow
 */
@Composable
fun WelcomeRoute(navController: NavController) {
    val koin = getKoin()

    WelcomeScreen(
        onWriteManualClick = {
            navController.navigate(OnboardingNavDestination.ManualInput)
        },
        onLetsGoClick = {
            FormScope.create(koin)
            navController.navigate(OnboardingNavDestination.Form(FIRST_FORM_PAGE_INDEX))
        },
    )
}
