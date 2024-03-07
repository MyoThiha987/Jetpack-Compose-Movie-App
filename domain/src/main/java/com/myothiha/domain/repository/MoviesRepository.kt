package com.myothiha.domain.repository

import com.myothiha.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:24 PM.
 **/
interface MoviesRepository {
    suspend fun syncMovies()
    fun retrieveMovies(): Flow<List<Movie>>
}