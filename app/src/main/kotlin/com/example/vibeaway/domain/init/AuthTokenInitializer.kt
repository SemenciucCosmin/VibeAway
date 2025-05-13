package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.domain.auth.AuthTokenDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Initializer for getting the Amadeus authentication token from API using API key and secret.
 */
class AuthTokenInitializer : Initializer<Unit>, KoinComponent {

    override fun create(context: Context) {
        get<AuthTokenDataSource>().initialize()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        KoinInitializer::class.java
    )
}
