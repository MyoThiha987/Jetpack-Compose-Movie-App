package com.myothiha.appbase.di

import com.myothiha.data.repository.AppThemeRepositoryImpl
import com.myothiha.data.repository.MoviesRepositoryImpl
import com.myothiha.domain.repository.AppThemeRepository
import com.myothiha.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @Author myothiha
 * Created 05/03/2024 at 10:00 PM.
 **/

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideUserDataRepository(impl: AppThemeRepositoryImpl): AppThemeRepository

}