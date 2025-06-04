package com.example.vibeaway.di

import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    single { Places.createClient(androidContext()) }
}
