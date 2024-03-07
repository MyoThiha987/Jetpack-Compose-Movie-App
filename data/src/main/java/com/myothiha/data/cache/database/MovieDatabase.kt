package com.myothiha.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myothiha.data.cache.database.dao.MovieDao
import com.myothiha.data.cache.entity.MovieEntity

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:44 PM.
 **/
@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}