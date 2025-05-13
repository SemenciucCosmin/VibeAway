package com.example.vibeaway.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vibeaway.ui.theme.VibeAwayTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VibeAwayTheme {}
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
    }
}
