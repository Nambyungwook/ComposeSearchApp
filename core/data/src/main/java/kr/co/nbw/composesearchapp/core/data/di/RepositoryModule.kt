package kr.co.nbw.composesearchapp.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.nbw.composesearchapp.core.data.repository.BooksRepositoryImpl
import kr.co.nbw.composesearchapp.core.domain.repository.BooksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindBooksRepository(
        booksRepositoryImpl: BooksRepositoryImpl
    ): BooksRepository
}