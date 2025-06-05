package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.BuildConfig
import com.example.vibeaway.R
import com.example.vibeaway.data.activity.di.activityDataModule
import com.example.vibeaway.data.activitydetails.di.activityDetailsDataModule
import com.example.vibeaway.data.auth.di.authDataModule
import com.example.vibeaway.data.database.di.databaseDataModule
import com.example.vibeaway.data.location.di.locationDataModule
import com.example.vibeaway.data.network.di.networkDataModule
import com.example.vibeaway.data.quiz.di.quizDataModule
import com.example.vibeaway.data.recommendation.di.recommendationDataModule
import com.example.vibeaway.di.appModule
import com.example.vibeaway.domain.locationdetails.di.locationDetailsDomainModule
import com.example.vibeaway.feature.activitydetails.di.activityDetailsFeatureModule
import com.example.vibeaway.feature.auth.di.authFeatureModule
import com.example.vibeaway.feature.category.di.categoryFeatureModule
import com.example.vibeaway.feature.feed.di.feedFeatureModule
import com.example.vibeaway.feature.locationdetails.di.locationDetailsFeatureModule
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
                    activityDataModule(),
                    activityDetailsDataModule(),
                    activityDetailsFeatureModule(),
                    appModule(),
                    authDataModule(defaultWebClientId),
                    authFeatureModule(),
                    categoryFeatureModule(),
                    databaseDataModule(),
                    feedFeatureModule(),
                    locationDataModule(),
                    locationDetailsDomainModule(),
                    locationDetailsFeatureModule(),
                    networkDataModule(
                        amadeusApiKey = BuildConfig.AMADEUS_API_KEY,
                        amadeusApiSecret = BuildConfig.AMADEUS_API_SECRET,
                    ),
                    onboardingFeatureModule(),
                    quizDataModule(),
                    recommendationDataModule(
                        googleAiApiKey = BuildConfig.GOOGLE_AI_API_KEY,
                    )
                )
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
