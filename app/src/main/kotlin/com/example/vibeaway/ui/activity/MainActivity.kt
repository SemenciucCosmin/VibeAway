package com.example.vibeaway.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.vibeaway.ui.navigation.RecommendationNavGraph
import com.example.vibeaway.ui.theme.VibeAwayTheme

/**
 * Activity for main experience
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            VibeAwayTheme {
                RecommendationNavGraph(navController)
            }
        }
    }

    companion object {
        fun startActivity(activity: AuthActivity) {
            val intent = Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }

            activity.startActivity(intent)
            activity.finish()
        }

        fun startActivity(activity: OnboardingActivity) {
            val intent = Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
