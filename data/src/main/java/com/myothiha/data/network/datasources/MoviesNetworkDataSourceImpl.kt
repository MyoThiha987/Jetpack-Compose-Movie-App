package com.myothiha.data.network.datasources

import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.data.di.KtorNetworkModule.pathUrl
import com.myothiha.data.network.dto.CreditResponse
import com.myothiha.data.network.dto.MovieDetailResponse
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 06/03/2024 at 1:50 AM.
 **/
class MoviesNetworkDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : MoviesNetworkDataSource {
    override suspend fun fetchNowPlayingMovies(): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_NOW_PLAYING)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchTopRatedMovies(): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_TOP_RATED)

        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchPopularMovies(): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_POPULAR)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchUpcomingMovies(): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_UPCOMING)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse {
        val data = client.get {
            pathUrl(Constants.GET_MOVIE_DETAIL + movieId)
        }.body<MovieDetailResponse>()
        return data
    }

    override suspend fun fetchCredits(movieId: Int): CreditResponse {
        val data = client.get {
            pathUrl("/movie/${movieId}${Constants.GET_CREDITS}")
        }.body<CreditResponse>()
        return data
    }
}