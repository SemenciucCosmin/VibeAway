package com.example.vibeaway.feature.locationdetails.route

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.locationdetails.component.LocationDetailsScreen
import com.example.vibeaway.feature.locationdetails.viewmodel.LocationDetailsViewModel
import com.example.vibeaway.ui.navigation.model.RecommendationNavDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.core.net.toUri

private const val GOOGLE_MAPS_URI = "geo:%s,%s"
private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"

@Composable
fun LocationDetailsRoute(
    locationDetailsId: String,
    navController: NavController,
) {
    val viewModel: LocationDetailsViewModel = koinViewModel(
        parameters = { parametersOf(locationDetailsId) }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LocationDetailsScreen(
        city = uiState.city,
        country = uiState.country,
        description = uiState.description,
        imageUrl = uiState.imageUrl,
        activities = uiState.activities,
        isLoading = uiState.isLoading,
        onActivityClick = { activityId ->
            val destination = RecommendationNavDestination.ActivityDetails(activityId)
            navController.navigate(destination)
        },
        onLocationClick = {
            val latitude = uiState.latitude ?: return@LocationDetailsScreen
            val longitude = uiState.longitude ?: return@LocationDetailsScreen

            val googleMapsUri = GOOGLE_MAPS_URI.format(latitude, longitude).toUri()
            val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri).apply {
                setPackage(GOOGLE_MAPS_PACKAGE)
            }

            if (googleMapsIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(googleMapsIntent)
            }
        }
    )
}
