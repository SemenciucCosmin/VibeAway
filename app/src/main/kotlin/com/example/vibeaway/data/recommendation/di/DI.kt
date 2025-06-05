package com.example.vibeaway.data.recommendation.di

import com.example.vibeaway.data.repository.RecommendationRepository
import com.example.vibeaway.data.repository.RecommendationRepositoryImpl
import org.koin.dsl.module

fun recommendationDataModule(googleAiApiKey: String) = module {
    single<RecommendationRepository> {
        RecommendationRepositoryImpl(
            googleAiApiKey = googleAiApiKey,
            activityCategoriesDataSource = get(),
            activitiesDataSource = get(),
            locationsDataSource = get(),
            activityDetailsDataSource = get(),
            databaseRepository = get(),
            googleAiApi = get(),
            context = get()
        )
    }
}
