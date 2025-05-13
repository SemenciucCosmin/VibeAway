package com.example.vibeaway.di

import com.example.vibeaway.data.datasource.ActivitiesDataSource
import com.example.vibeaway.data.datasource.ActivitiesDataSourceImpl
import com.example.vibeaway.data.datasource.ActivityCategoriesDataSource
import com.example.vibeaway.data.datasource.ActivityCategoriesDataSourceImpl
import com.example.vibeaway.data.datasource.BFIQuestionsDataSource
import com.example.vibeaway.data.datasource.BFIQuestionsDataSourceImpl
import com.example.vibeaway.data.datasource.LocationsDataSource
import com.example.vibeaway.data.datasource.LocationsDataSourceImpl
import com.example.vibeaway.data.repository.RecommendationRepository
import com.example.vibeaway.data.repository.RecommendationRepositoryImpl
import com.example.vibeaway.domain.auth.AuthTokenDataSource
import com.example.vibeaway.domain.auth.AuthTokenDataSourceImpl
import com.example.vibeaway.network.AuthTokenInterceptor
import com.example.vibeaway.network.AuthTokenManager
import com.example.vibeaway.network.service.AuthApi
import com.example.vibeaway.network.service.LocationApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.jvm.java

fun appModule() = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}

fun authModule(
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
}

fun dataModule() = module {
    single<ActivityCategoriesDataSource> { ActivityCategoriesDataSourceImpl() }
    single<ActivitiesDataSource> { ActivitiesDataSourceImpl() }
    single<BFIQuestionsDataSource> { BFIQuestionsDataSourceImpl() }
    single<LocationsDataSource> { LocationsDataSourceImpl() }
    factory<RecommendationRepository> { RecommendationRepositoryImpl(get(), get()) }
}

fun networkModule() = module {
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
    single { get<Retrofit>().create(LocationApi::class.java) }
}
