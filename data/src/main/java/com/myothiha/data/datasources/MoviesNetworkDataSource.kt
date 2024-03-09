package com.myothiha.data.datasources

import com.myothiha.data.network.dto.CreditResponse
import com.myothiha.data.network.dto.MovieDetailResponse
import com.myothiha.data.network.dto.MovieDto

/**
 * @Author myothiha
 * Created 05/03/2024 at 8:56 PM.
 **/
interface MoviesNetworkDataSource {
    suspend fun fetchNowPlayingMovies(): List<MovieDto>
    suspend fun fetchTopRatedMovies(): List<MovieDto>
    suspend fun fetchPopularMovies(): List<MovieDto>
    suspend fun fetchUpcomingMovies(): List<MovieDto>
    suspend fun fetchMovieDetail(movieId : Int): MovieDetailResponse
    suspend fun fetchCredits(movieId : Int): CreditResponse

}