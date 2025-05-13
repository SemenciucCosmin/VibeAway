package com.example.vibeaway.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vibeaway.feature.auth.route.AuthRoute
import com.example.vibeaway.ui.theme.VibeAwayTheme

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VibeAwayTheme {
                AuthRoute(
                    onSigned = { MainActivity.Companion.startActivity(this) }
                )
            }
        }
    }

    companion object {
        fun startActivity(activity: MainActivity) {
            val intent = Intent(activity, AuthActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
