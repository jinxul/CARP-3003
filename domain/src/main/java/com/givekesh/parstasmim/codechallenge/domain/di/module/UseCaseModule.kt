package com.givekesh.parstasmim.codechallenge.domain.di.module

import com.givekesh.parstasmim.codechallenge.domain.usecase.books.BooksUseCase
import com.givekesh.parstasmim.codechallenge.domain.usecase.books.BooksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindBooksUseCase(impl: BooksUseCaseImpl): BooksUseCase
}