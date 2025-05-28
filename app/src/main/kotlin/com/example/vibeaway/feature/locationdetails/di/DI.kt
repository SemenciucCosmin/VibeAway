package com.example.vibeaway.feature.locationdetails.di

import com.example.vibeaway.feature.locationdetails.viewmodel.LocationDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun locationDetailsFeatureModule() = module {
    viewModelOf(::LocationDetailsViewModel)
}
