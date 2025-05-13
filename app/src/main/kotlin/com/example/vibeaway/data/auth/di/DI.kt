package com.example.vibeaway.data.auth.di

import com.example.vibeaway.data.auth.datasource.GoogleCredentialDataSource
import com.example.vibeaway.data.auth.repository.AuthRepository
import com.example.vibeaway.data.auth.repository.AuthRepositoryImpl
import org.koin.dsl.module

fun authDataModule(defaultWebClientId: String) = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    factory<GoogleCredentialDataSource> {
        GoogleCredentialDataSource(
            context = get(),
            defaultWebClientId = defaultWebClientId,
        )
    }
}
