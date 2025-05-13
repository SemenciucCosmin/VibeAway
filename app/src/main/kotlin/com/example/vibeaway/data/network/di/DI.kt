package com.example.vibeaway.data.network.di

import com.example.vibeaway.data.network.AuthTokenInterceptor
import com.example.vibeaway.data.network.AuthTokenManager
import com.example.vibeaway.data.network.datasource.AuthTokenDataSource
import com.example.vibeaway.data.network.datasource.AuthTokenDataSourceImpl
import com.example.vibeaway.data.network.service.AuthApi
import com.example.vibeaway.data.network.service.LocationApi
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
    single { AuthTokenManager() }
    factory<AuthTokenDataSource> {
        AuthTokenDataSourceImpl(
            amadeusApiKey = amadeusApiKey,
            amadeusApiSecret = amadeusApiSecret,
            authApi = get(),
            authTokenManager = get(),
            coroutineScope = get()
        )
    }

    val json = Json { ignoreUnknownKeys = true }

    single { AuthTokenInterceptor(get()) }

    single {
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(get<AuthTokenInterceptor>())
                    .build()
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
    single<LocationApi> { get<Retrofit>().create(LocationApi::class.java) }
}
