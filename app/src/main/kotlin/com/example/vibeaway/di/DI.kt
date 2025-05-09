package com.example.vibeaway.di

import com.example.vibeaway.data.datasource.ActivitiesDataSource
import com.example.vibeaway.data.datasource.ActivitiesDataSourceImpl
import com.example.vibeaway.data.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.datasource.ActivityCategoriesDataSourceImpl
import com.example.vibeaway.data.datasource.BFIQuestionsDataSource
import com.example.vibeaway.data.datasource.BFIQuestionsDataSourceImpl
import org.koin.dsl.module

fun dataModule() = module {
    single<ActivityCategoriesDataSource> { ActivityCategoriesDataSourceImpl() }
    single<ActivitiesDataSource> { ActivitiesDataSourceImpl() }
    single<BFIQuestionsDataSource> { BFIQuestionsDataSourceImpl() }
}
