package com.example.vibeaway.data.recommendation.di

import com.example.vibeaway.data.recommendation.datasource.ActivitiesDataSource
import com.example.vibeaway.data.recommendation.datasource.ActivitiesDataSourceImpl
import com.example.vibeaway.data.recommendation.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.recommendation.datasource.ActivityCategoriesDataSourceImpl
import com.example.vibeaway.data.recommendation.datasource.LocationsDataSource
import com.example.vibeaway.data.recommendation.datasource.LocationsDataSourceImpl
import com.example.vibeaway.data.repository.RecommendationRepository
import com.example.vibeaway.data.repository.RecommendationRepositoryImpl
import org.koin.dsl.module

fun recommendationDataModule() = module {
    single<ActivityCategoriesDataSource> { ActivityCategoriesDataSourceImpl() }
    single<ActivitiesDataSource> { ActivitiesDataSourceImpl() }
    single<LocationsDataSource> { LocationsDataSourceImpl() }
    factory<RecommendationRepository> { RecommendationRepositoryImpl(get(), get()) }
}
