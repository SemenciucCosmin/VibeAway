package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.BuildConfig
import com.google.android.libraries.places.api.Places
import org.koin.core.component.KoinComponent

/**
 * Initializer for getting the Amadeus authentication token from API using API key and secret.
 */
class GooglePlacesInitializer : Initializer<Unit>, KoinComponent {

    override fun create(context: Context) {
        Places.initializeWithNewPlacesApiEnabled(context, BuildConfig.GOOGLE_PLACES_API_KEY)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        KoinInitializer::class.java
    )
}
