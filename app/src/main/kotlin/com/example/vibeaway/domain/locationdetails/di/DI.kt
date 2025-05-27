package com.example.vibeaway.domain.locationdetails.di

import com.example.vibeaway.domain.locationdetails.usecase.GetPopularLocationDetailsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun locationDetailsDomainModule() = module {
    factoryOf(::GetPopularLocationDetailsUseCase)
}
