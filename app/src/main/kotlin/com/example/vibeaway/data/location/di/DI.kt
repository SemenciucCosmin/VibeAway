package com.example.vibeaway.data.location.di

import com.example.vibeaway.data.location.datasource.LocationsDataSource
import com.example.vibeaway.data.location.datasource.LocationsDataSourceImpl
import org.koin.dsl.module

fun locationDataModule() = module {
    single<LocationsDataSource> { LocationsDataSourceImpl() }
}
