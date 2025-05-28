package com.example.vibeaway.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.vibeaway.feature.category.route.CategoryRoute
import com.example.vibeaway.feature.feed.route.FeedRoute
import com.example.vibeaway.feature.locationdetails.route.LocationDetailsRoute
import com.example.vibeaway.ui.navigation.model.RecommendationNavDestination

/**
 * Navigation graph for recommendation flow
 */
@Composable
fun RecommendationNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = RecommendationNavDestination.Feed,
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
        composable<RecommendationNavDestination.Feed> {
            FeedRoute(navController)
        }

        composable<RecommendationNavDestination.Category> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<RecommendationNavDestination.Category>()
            CategoryRoute(
                categoryId = args.id,
                navController = navController
            )
        }

        composable<RecommendationNavDestination.LocationDetails> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<RecommendationNavDestination.LocationDetails>()
            LocationDetailsRoute(
                locationDetailsId = args.id,
                navController = navController
            )
        }

        composable<RecommendationNavDestination.ActivityDetails> { navBackStackEntry ->

        }
    }
}
