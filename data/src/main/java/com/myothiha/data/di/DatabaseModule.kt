package com.myothiha.data.di

import android.content.Context
import androidx.room.Room
import com.myothiha.data.cache.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:21 PM.
 **/

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context = context,
            MovieDatabase::class.java, name = "MOVIE.DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}