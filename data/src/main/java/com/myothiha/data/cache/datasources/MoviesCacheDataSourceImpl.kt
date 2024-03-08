package com.myothiha.data.cache.datasources

import androidx.room.withTransaction
import com.myothiha.data.cache.database.MovieDatabase
import com.myothiha.data.cache.entity.MovieEntity
import com.myothiha.data.datasources.MoviesCacheDataSource
import com.myothiha.data.network.dto.toDomain
import com.myothiha.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:13 PM.
 **/
class MoviesCacheDataSourceImpl @Inject constructor(
    private val database: MovieDatabase
) : MoviesCacheDataSource {
    override suspend fun saveMovies(data: List<MovieEntity>,movieType : Int) {
        database.withTransaction {
            database.movieDao().deleteCacheMovies(movieType = movieType)
            database.movieDao().saverMovies(data = data)
        }
    }

    override fun retrieveCacheMovies(): Flow<List<Movie>> {
        return database.movieDao().retrieveCacheMovies().map { movieEntityList ->
            movieEntityList.map {
                it.toDomain()
            }
        }
    }
}