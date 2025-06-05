package com.example.vibeaway.feature.activitydetails.route

import android.R.attr.label
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.activitydetails.component.ActivityDetailsScreen
import com.example.vibeaway.feature.activitydetails.viewmodel.ActivityDetailsViewModel
import com.example.vibeaway.ui.navigation.model.RecommendationNavDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val GOOGLE_MAPS_URI = "geo:0,0?q=%s,%s(%s)"
private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"

@Composable
fun ActivityDetailsRoute(
    activityId: String,
    navController: NavController,
) {
    val viewModel: ActivityDetailsViewModel = koinViewModel(
        parameters = { parametersOf(activityId) }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ActivityDetailsScreen(
        activity = uiState.currentActivity,
        relatedActivities = uiState.relatedActivities,
        onFavouriteClicked = viewModel::changeFavouriteState,
        onLocationClick = {
            uiState.currentActivity?.let { activity ->
                val googleMapsUri = GOOGLE_MAPS_URI.format(
                    activity.latitude,
                    activity.longitude,
                    activity.title
                ).toUri()

                val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri).apply {
                    setPackage(GOOGLE_MAPS_PACKAGE)
                }

                if (googleMapsIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(googleMapsIntent)
                }
            }
        },
        onLocationDetailsClick = {
            uiState.currentActivity?.locationId?.let { locationDetailsId ->
                val destination = RecommendationNavDestination.LocationDetails(locationDetailsId)
                navController.navigate(destination)
            }
        },
        onActivityDetailsClick = { activityDetailsId ->
            val destination = RecommendationNavDestination.ActivityDetails(activityDetailsId)
            navController.navigate(destination)
        }
    )
}

fun openMapWithMarker(context: Context, latitude: Double, longitude: Double, label: String) {
    val uri = "geo:0,0?q=$latitude,$longitude($label)".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // fallback: open in browser
        val browserUri = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude".toUri()
        context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
    }
}
