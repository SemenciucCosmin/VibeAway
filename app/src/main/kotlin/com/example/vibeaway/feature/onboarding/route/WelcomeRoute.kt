package com.example.vibeaway.feature.onboarding.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.vibeaway.feature.onboarding.component.WelcomeScreen
import com.example.vibeaway.feature.onboarding.navigation.model.OnboardingNavDestination

@Composable
fun WelcomeRoute(navController: NavController) {
    WelcomeScreen(
        onWriteManualClick = { navController.navigate(OnboardingNavDestination.ManualInput) },
        onLetsGoClick = { navController.navigate(OnboardingNavDestination.ManualInput) },
    )
}
