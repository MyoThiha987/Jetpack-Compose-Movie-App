package com.myothiha.data.datasources

import androidx.paging.PagingData
import com.myothiha.data.network.dto.CreditResponse
import com.myothiha.data.network.dto.MovieDetailResponse
import com.myothiha.data.network.dto.MovieDto
import kotlinx.coroutines.flow.Flow

/**
 * @Author myothiha
 * Created 05/03/2024 at 8:56 PM.
 **/
interface MoviesNetworkDataSource {
    suspend fun fetchNowPlayingMovies(page: Int): List<MovieDto>
    suspend fun fetchTopRatedMovies(page: Int): List<MovieDto>
    suspend fun fetchPopularMovies(page: Int): List<MovieDto>
    suspend fun fetchUpcomingMovies(): List<MovieDto>
    suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse
    suspend fun fetchCredits(movieId: Int): CreditResponse
    fun fetchPagingMovies(movieType: Int): Flow<PagingData<MovieDto>>
    fun searchMovies(query: String): Flow<PagingData<MovieDto>>

}