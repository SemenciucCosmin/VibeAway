package com.example.vibeaway.domain.init

import android.content.Context
import androidx.startup.Initializer
import com.example.vibeaway.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        startKoin {
            androidContext(context.applicationContext)
            modules(
                listOf(
                    dataModule()
                )
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
