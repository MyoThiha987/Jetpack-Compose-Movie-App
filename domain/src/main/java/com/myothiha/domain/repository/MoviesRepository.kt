package com.myothiha.domain.repository

import com.myothiha.domain.model.Credit
import com.myothiha.domain.model.Movie
import com.myothiha.domain.model.MovieDetail
import com.myothiha.domain.model.MovieFullDetail
import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:24 PM.
 **/
interface MoviesRepository {
    suspend fun syncMovies()
    fun retrieveMovies(): Flow<List<Movie>>
    suspend fun retrieveMovieDetail(movieId: Int): MovieFullDetail
}