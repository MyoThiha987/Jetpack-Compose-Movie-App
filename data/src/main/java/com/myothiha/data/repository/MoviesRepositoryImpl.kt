package com.myothiha.data.repository

import com.myothiha.data.datasources.MoviesCacheDataSource
import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.domain.model.Movie
import com.myothiha.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:21 PM.
 **/
class MoviesRepositoryImpl @Inject constructor(
    private val cacheDataSource: MoviesCacheDataSource,
    private val dataSource: MoviesNetworkDataSource
) : MoviesRepository {
    override suspend fun syncMovies() {
        val data = dataSource.fetchMovies()
    }

    override fun retrieveMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}