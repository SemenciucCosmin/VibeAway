package com.example.vibeaway.domain.init

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

@Suppress("unused")
class StartupInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Log.d(TAG, "Dependencies initialized.")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        KoinInitializer::class.java,
        AuthTokenInitializer::class.java
    )

    companion object {
        private const val TAG = "StartupInitializer"
    }
}
