package com.givekesh.parstasmim.codechallenge.data.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MoshiModule {
    @Singleton
    @Provides
    fun provideKotlinJsonAdapter(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Singleton
    @Provides
    fun provideMoshiBuilder(
        kotlinJsonAdapter: KotlinJsonAdapterFactory
    ): Moshi = Moshi.Builder().add(kotlinJsonAdapter).build()

    @Singleton
    @Provides
    fun provideMoshiConverter(
        moshi: Moshi
    ): MoshiConverterFactory = MoshiConverterFactory.create(moshi)
}