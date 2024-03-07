package com.myothiha.appbase.di

import com.myothiha.appbase.dispatcher.DefaultDispatcherProvider
import com.myothiha.appbase.exception.ExceptionMapper
import com.myothiha.appbase.exception.ExceptionMapperImpl
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author myothiha
 * Created 05/03/2024 at 10:08 PM.
 **/

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    @Singleton
    abstract fun provideException(impl: ExceptionMapperImpl): ExceptionMapper

    @Binds
    abstract fun provideDispatcher(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}