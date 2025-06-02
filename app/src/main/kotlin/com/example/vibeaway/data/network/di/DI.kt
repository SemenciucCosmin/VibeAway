package com.example.vibeaway.data.network.di

import com.example.vibeaway.data.network.AuthTokenInterceptor
import com.example.vibeaway.data.network.AuthTokenManager
import com.example.vibeaway.data.network.TokenAuthenticator
import com.example.vibeaway.data.network.datasource.AuthTokenDataSource
import com.example.vibeaway.data.network.datasource.AuthTokenDataSourceImpl
import com.example.vibeaway.data.network.service.ActivitiesDetailsApi
import com.example.vibeaway.data.network.service.AuthApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.jvm.java

fun networkDataModule(
    amadeusApiKey: String,
    amadeusApiSecret: String,
) = module {
    val json = Json { ignoreUnknownKeys = true }

    single { AuthTokenManager() }
    factory<AuthTokenDataSource> {
        AuthTokenDataSourceImpl(
            amadeusApiKey = amadeusApiKey,
            amadeusApiSecret = amadeusApiSecret,
            authApi = get(),
            authTokenManager = get(),
            coroutineScope = get(),
        )
    }

    single {
        AuthTokenInterceptor(get())
    }

    single {
        TokenAuthenticator(
            amadeusApiKey = amadeusApiKey,
            amadeusApiSecret = amadeusApiSecret,
            authTokenManager = get(),
            authApi = get()
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthTokenInterceptor>())
            .authenticator(get<TokenAuthenticator>())
            .build()
    }

    single<AuthApi> {
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AuthApi::class.java)
    }

    single<ActivitiesDetailsApi> {
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com")
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ActivitiesDetailsApi::class.java)
    }
}
