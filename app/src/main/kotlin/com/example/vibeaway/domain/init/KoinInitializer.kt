package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.BuildConfig
import com.example.vibeaway.di.appModule
import com.example.vibeaway.di.authModule
import com.example.vibeaway.di.dataModule
import com.example.vibeaway.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        startKoin {
            androidContext(context.applicationContext)
            modules(
                listOf(
                    appModule(),
                    authModule(
                        amadeusApiKey = BuildConfig.AMADEUS_API_KEY,
                        amadeusApiSecret = BuildConfig.AMADEUS_API_SECRET,
                    ),
                    dataModule(),
                    networkModule(),
                )
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
