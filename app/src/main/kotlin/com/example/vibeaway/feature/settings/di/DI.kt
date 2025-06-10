package com.example.vibeaway.feature.settings.di

import com.example.vibeaway.feature.settings.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun settingsFeatureModule() = module {
    viewModelOf(::SettingsViewModel)
}
