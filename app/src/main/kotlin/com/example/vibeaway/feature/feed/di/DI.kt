package com.example.vibeaway.feature.feed.di

import com.example.vibeaway.feature.feed.viewmodel.FeedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun feedFeatureModule() = module {
    viewModelOf(::FeedViewModel)
}
