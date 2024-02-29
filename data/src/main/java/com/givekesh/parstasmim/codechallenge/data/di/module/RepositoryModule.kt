package com.givekesh.parstasmim.codechallenge.data.di.module

import com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books.BooksRepository
import com.givekesh.parstasmim.codechallenge.data.source.remote.repository.books.BooksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindBooksRepository(impl: BooksRepositoryImpl): BooksRepository
}