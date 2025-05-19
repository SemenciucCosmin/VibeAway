package com.example.vibeaway.feature.onboarding.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.vibeaway.feature.onboarding.navigation.model.OnboardingNavDestination
import com.example.vibeaway.feature.onboarding.route.FormRoute
import com.example.vibeaway.feature.onboarding.route.ManualInputRoute
import com.example.vibeaway.feature.onboarding.route.WelcomeRoute

/**
 * Navigation graph for onboarding flow
 */
@Composable
fun OnboardingNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnboardingNavDestination.Welcome,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
    ) {
        composable<OnboardingNavDestination.Welcome> {
            WelcomeRoute(navController)
        }

        composable<OnboardingNavDestination.ManualInput> {
            ManualInputRoute(navController)
        }

        composable<OnboardingNavDestination.Form> { nacBackStackEntry ->
            val args = nacBackStackEntry.toRoute<OnboardingNavDestination.Form>()

            FormRoute(
                index = args.index,
                navController = navController
            )
        }
    }
}
