package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.BuildConfig
import com.example.vibeaway.R
import com.example.vibeaway.data.auth.di.authDataModule
import com.example.vibeaway.data.network.di.networkDataModule
import com.example.vibeaway.data.quiz.di.quizDataModule
import com.example.vibeaway.data.recommendation.di.recommendationDataModule
import com.example.vibeaway.di.appModule
import com.example.vibeaway.feature.auth.di.authFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val defaultWebClientId = context.getString(R.string.lbl_no)

        startKoin {
            androidContext(context.applicationContext)
            modules(
                listOf(
                    appModule(),
                    authDataModule(defaultWebClientId),
                    authFeatureModule(),
                    networkDataModule(
                        amadeusApiKey = BuildConfig.AMADEUS_API_KEY,
                        amadeusApiSecret = BuildConfig.AMADEUS_API_SECRET,
                    ),
                    quizDataModule(),
                    recommendationDataModule()
                )
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
