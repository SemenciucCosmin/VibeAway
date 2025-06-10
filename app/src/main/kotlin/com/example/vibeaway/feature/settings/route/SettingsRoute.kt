package com.example.vibeaway.feature.settings.route

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import com.example.vibeaway.feature.settings.components.SettingsScreen
import com.example.vibeaway.feature.settings.viewmodel.SettingsViewModel
import com.example.vibeaway.feature.settings.viewmodel.model.SettingsEvent
import com.example.vibeaway.ui.activity.AuthActivity
import com.example.vibeaway.ui.activity.MainActivity
import com.example.vibeaway.ui.activity.OnboardingActivity
import com.example.vibeaway.ui.core.components.EventHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoute() {
    val viewModel: SettingsViewModel = koinViewModel()
    val activity = LocalActivity.current as? MainActivity

    SettingsScreen(
        onResetBfiScoresClick = viewModel::resetBfiScores,
        onSignOutClick = viewModel::signOut
    )

    EventHandler(viewModel.events) { event ->
        when (event) {
            SettingsEvent.START_ONBOARDING -> {
                viewModel.unregisterEvent(event)
                activity?.let { OnboardingActivity.startActivity(activity) }
            }

            SettingsEvent.SIGN_OUT -> {
                viewModel.unregisterEvent(event)
                activity?.let { AuthActivity.startActivity(activity) }
            }
        }
    }
}
