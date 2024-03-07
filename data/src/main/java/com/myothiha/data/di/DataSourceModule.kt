package com.myothiha.data.di

import com.myothiha.data.cache.datasources.MoviesCacheDataSourceImpl
import com.myothiha.data.datasources.MoviesCacheDataSource
import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.data.network.datasources.MoviesNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @Author myothiha
 * Created 07/03/2024 at 10:55 PM.
 **/

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideMoviesNetworkDataSource(impl: MoviesNetworkDataSourceImpl): MoviesNetworkDataSource

    @Binds
    abstract fun provideMoviesCacheDataSource(impl: MoviesCacheDataSourceImpl): MoviesCacheDataSource
}