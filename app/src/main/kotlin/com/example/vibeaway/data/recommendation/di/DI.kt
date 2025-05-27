package com.example.vibeaway.data.recommendation.di

import com.example.vibeaway.data.repository.RecommendationRepository
import com.example.vibeaway.data.repository.RecommendationRepositoryImpl
import org.koin.dsl.module

fun recommendationDataModule() = module {
    factory<RecommendationRepository> { RecommendationRepositoryImpl(get(), get(), get(), get()) }
}
