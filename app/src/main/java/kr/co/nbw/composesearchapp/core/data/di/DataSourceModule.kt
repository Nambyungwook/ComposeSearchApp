package kr.co.nbw.composesearchapp.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.nbw.composesearchapp.core.data.datasource.local.LocalBookDataSource
import kr.co.nbw.composesearchapp.core.data.datasource.local.LocalBookDataSourceImpl
import kr.co.nbw.composesearchapp.core.data.datasource.remote.RemoteSearchBookDataSource
import kr.co.nbw.composesearchapp.core.data.datasource.remote.RemoteSearchBookDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindRemoteSearchBookDataSource(
        remoteSearchBookDataSourceImpl: RemoteSearchBookDataSourceImpl
    ): RemoteSearchBookDataSource

    @Singleton
    @Binds
    abstract fun bindLocalFavoriteBookDataSource(
        localFavoriteBookDataSourceImpl: LocalBookDataSourceImpl
    ): LocalBookDataSource
}