package com.givekesh.parstasmim.codechallenge.data.di.module

import com.givekesh.parstasmim.codechallenge.data.source.remote.api.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkApiModule {
    @Singleton
    @Provides
    fun provideBooksApi(
        retrofit: Retrofit.Builder
    ): BooksApi = retrofit.build().create(BooksApi::class.java)
}