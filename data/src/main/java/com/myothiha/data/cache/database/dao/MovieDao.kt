package com.myothiha.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myothiha.data.cache.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:45 PM.
 **/

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverMovies(data: List<MovieEntity>)

    @Query("SELECT * from movies")
    fun retrieveCacheMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isLiked=:isLiked WHERE movieType=:movieType and id=:movieId")
    suspend fun updateSaveMovie(movieId: Int, isLiked: Boolean, movieType: Int)

    @Query("DELETE from movies WHERE movieType=:movieType")
    suspend fun deleteCacheMovies(movieType: Int)

    @Query("SELECT * from movies WHERE isLiked=:isLiked")
    fun retrieveBookmarkCacheMovies(isLiked: Boolean = true): Flow<List<MovieEntity>>

    @Query("SELECT id from movies WHERE isLiked=:isLiked AND movieType=:movieType ")
    fun retrieveBookmarkedMovies(isLiked: Boolean = true,movieType: Int): List<Int>

}