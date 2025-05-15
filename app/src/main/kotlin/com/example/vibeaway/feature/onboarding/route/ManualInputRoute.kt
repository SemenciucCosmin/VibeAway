package com.example.vibeaway.feature.onboarding.route

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vibeaway.feature.onboarding.component.ManualInputScreen
import com.example.vibeaway.feature.onboarding.viewmodel.ManualInputViewModel
import com.example.vibeaway.feature.onboarding.viewmodel.model.ManualInputUiState
import com.example.vibeaway.ui.activity.MainActivity
import com.example.vibeaway.ui.activity.OnboardingActivity
import com.example.vibeaway.ui.core.components.EventHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun ManualInputRoute(navController: NavController) {
    val viewModel = koinViewModel<ManualInputViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current as? OnboardingActivity

    ManualInputScreen(
        bfiDimensionsScores = uiState.bfiDimensionsScores,
        onScoreChange = viewModel::changeScore,
        onBack = navController::navigateUp,
        onLetsGoClick = { viewModel.saveScores() }
    )

    EventHandler(viewModel.events) { event ->
        when (event) {
            ManualInputUiState.Event.ONBOARDING_DONE -> {
                viewModel.unregisterEvent(event)
                activity?.let(MainActivity::startActivity)
            }
        }
    }
}
