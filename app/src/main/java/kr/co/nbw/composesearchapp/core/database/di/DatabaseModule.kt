package kr.co.nbw.composesearchapp.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.nbw.composesearchapp.core.database.BookDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // Room
    @Singleton
    @Provides
    fun provideFavoriteBookDatabase(@ApplicationContext context: Context): BookDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            "favorite-books"
        ).build()
}