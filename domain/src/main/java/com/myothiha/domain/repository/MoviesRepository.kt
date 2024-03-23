package com.myothiha.domain.repository

import androidx.paging.PagingData
import com.myothiha.domain.model.Movie
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
    suspend fun updateSavedMovie(movieId: Int, isLiked: Boolean, movieType: Int)
    suspend fun retrieveSavedMovies(): Flow<List<Movie>>
    fun fetchPagingMovies(movieType : Int) : Flow<PagingData<Movie>>
}