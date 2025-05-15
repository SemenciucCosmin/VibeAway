package com.example.vibeaway.data.database.di

import com.example.vibeaway.data.database.repository.DatabaseRepository
import com.example.vibeaway.data.database.repository.DatabaseRepositoryImpl
import org.koin.dsl.module

fun databaseDataModule() = module {
    factory<DatabaseRepository> { DatabaseRepositoryImpl() }
}
