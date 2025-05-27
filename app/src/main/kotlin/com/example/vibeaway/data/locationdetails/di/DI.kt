package com.example.vibeaway.data.locationdetails.di

import com.example.vibeaway.data.locationdetails.datasource.LocationDetailsDataSource
import com.example.vibeaway.data.locationdetails.datasource.LocationDetailsDataSourceImpl
import org.koin.dsl.module

fun locationDetailsDataModule() = module {
    factory<LocationDetailsDataSource> { LocationDetailsDataSourceImpl() }
}
