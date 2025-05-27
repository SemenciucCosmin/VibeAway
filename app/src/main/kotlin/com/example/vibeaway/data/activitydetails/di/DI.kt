package com.example.vibeaway.data.activitydetails.di

import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSource
import com.example.vibeaway.data.activitydetails.datasource.ActivityDetailsDataSourceImpl
import org.koin.dsl.module

fun activityDetailsDataModule() = module {
    factory<ActivityDetailsDataSource> { ActivityDetailsDataSourceImpl() }
}
