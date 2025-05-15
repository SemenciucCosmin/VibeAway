package com.example.vibeaway.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.vibeaway.feature.onboarding.navigation.OnboardingNavGraph
import com.example.vibeaway.feature.onboarding.viewmodel.OnboardingViewModel
import com.example.vibeaway.feature.onboarding.viewmodel.model.OnboardingEvent
import com.example.vibeaway.ui.core.components.EventHandler
import com.example.vibeaway.ui.theme.VibeAwayTheme
import org.koin.androidx.compose.koinViewModel

class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: OnboardingViewModel = koinViewModel()
            val navController = rememberNavController()

            VibeAwayTheme {
                OnboardingNavGraph(
                    navController = navController
                )
            }

            EventHandler(viewModel.events) { event ->
                when (event) {
                    OnboardingEvent.ONBOARDING_DONE -> {
                        viewModel.unregisterEvent(event)
                        MainActivity.startActivity(this)
                    }
                }
            }
        }
    }

    companion object {
        fun startActivity(activity: AuthActivity) {
            val intent = Intent(activity, OnboardingActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
