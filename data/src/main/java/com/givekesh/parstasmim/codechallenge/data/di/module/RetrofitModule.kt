package com.givekesh.parstasmim.codechallenge.data.di.module

import com.givekesh.parstasmim.codechallenge.data.BuildConfig
import com.givekesh.parstasmim.codechallenge.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().also {
        it.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkhttp(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideDefaultRetrofit(
        factory: MoshiConverterFactory,
        client: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(factory)
}