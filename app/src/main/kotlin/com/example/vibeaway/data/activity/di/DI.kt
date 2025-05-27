package com.example.vibeaway.data.activity.di

import com.example.vibeaway.data.activity.datasource.ActivitiesDataSource
import com.example.vibeaway.data.activity.datasource.ActivitiesDataSourceImpl
import com.example.vibeaway.data.activity.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.activity.datasource.ActivityCategoriesDataSourceImpl
import org.koin.dsl.module

fun activityDataModule() = module {
    single<ActivityCategoriesDataSource> { ActivityCategoriesDataSourceImpl() }
    single<ActivitiesDataSource> { ActivitiesDataSourceImpl() }
}
