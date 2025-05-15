package com.example.vibeaway.feature.onboarding.di

import com.example.vibeaway.feature.onboarding.viewmodel.ManualInputViewModel
import com.example.vibeaway.feature.onboarding.viewmodel.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun onboardingFeatureModule() = module {
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::ManualInputViewModel)
}
