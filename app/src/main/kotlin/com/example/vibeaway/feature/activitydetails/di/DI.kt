package com.example.vibeaway.feature.activitydetails.di

import com.example.vibeaway.feature.activitydetails.viewmodel.ActivityDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun activityDetailsFeatureModule() = module {
    viewModelOf(::ActivityDetailsViewModel)
}
