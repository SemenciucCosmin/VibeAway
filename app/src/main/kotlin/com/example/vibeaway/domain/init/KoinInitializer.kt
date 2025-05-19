package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.BuildConfig
import com.example.vibeaway.R
import com.example.vibeaway.data.auth.di.authDataModule
import com.example.vibeaway.data.database.di.databaseDataModule
import com.example.vibeaway.data.network.di.networkDataModule
import com.example.vibeaway.data.quiz.di.quizDataModule
import com.example.vibeaway.data.recommendation.di.recommendationDataModule
import com.example.vibeaway.di.appModule
import com.example.vibeaway.feature.auth.di.authFeatureModule
import com.example.vibeaway.feature.onboarding.di.onboardingFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Initializer for Koin dependency injection modules.
 */
class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val defaultWebClientId = context.getString(R.string.default_web_client_id)

        startKoin {
            androidContext(context.applicationContext)
            modules(
                listOf(
                    appModule(),
                    authDataModule(defaultWebClientId),
                    authFeatureModule(),
                    databaseDataModule(),
                    networkDataModule(
                        amadeusApiKey = BuildConfig.AMADEUS_API_KEY,
                        amadeusApiSecret = BuildConfig.AMADEUS_API_SECRET,
                    ),
                    onboardingFeatureModule(),
                    quizDataModule(),
                    recommendationDataModule()
                )
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
