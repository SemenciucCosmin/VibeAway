package com.example.vibeaway.feature.auth.di

import com.example.vibeaway.feature.auth.viewmodel.AuthViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun authFeatureModule() = module {
    factoryOf(::AuthViewModel)
}
