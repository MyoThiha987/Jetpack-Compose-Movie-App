package com.myothiha.data.network.datasources

import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
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
            url(Constants.GET_NOW_PLAYING)
            parameter("language", "en")
            parameter("page", 1)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchTopRatedMovies(): List<MovieDto> {
        val data = client.get {
            url(Constants.GET_TOP_RATED)
            parameter("language", "en")
            parameter("page", 1)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchPopularMovies(): List<MovieDto> {
        val data = client.get {
            url(Constants.GET_POPULAR)
            parameter("language", "en")
            parameter("page", 1)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchUpcomingMovies(): List<MovieDto> {
        val data = client.get {
            url(Constants.GET_UPCOMING)
            parameter("language", "en")
            parameter("page", 1)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }
}