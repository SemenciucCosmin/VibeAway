package com.example.vibeaway.feature.category.di

import com.example.vibeaway.feature.category.viewmodel.CategoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun categoryFeatureModule() = module {
    viewModelOf(::CategoryViewModel)
}
