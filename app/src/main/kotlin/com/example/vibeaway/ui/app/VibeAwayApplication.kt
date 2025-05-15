package com.example.vibeaway.ui.app

import android.app.Application
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VibeAwayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureFirebaseServices()
    }

    private fun configureFirebaseServices() {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(AUTH_HOST, AUTH_PORT)
            Firebase.firestore.useEmulator(FIRESTORE_HOST, FIRESTORE_PORT)
        }

        FirebaseApp.initializeApp(this)
    }

    companion object {
        const val AUTH_HOST = "127.0.0.1"
        const val AUTH_PORT = 9099
        const val FIRESTORE_HOST = "http://127.0.0.1"
        const val FIRESTORE_PORT = 8080
    }
}
