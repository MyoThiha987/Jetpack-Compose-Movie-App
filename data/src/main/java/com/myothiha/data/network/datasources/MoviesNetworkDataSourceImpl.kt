package com.myothiha.data.network.datasources

import android.util.Log
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
    override suspend fun fetchMovies(): List<MovieDto> {
        val data = client.get {
            url(Constants.GET_TOP_RATED)
            parameter("language", "US")
            parameter("page", 1)
        }.body<DataResponse<MovieDto>>()
        Log.d("DATA_NET", data.data.orEmpty().toString())
        return data.data.orEmpty()
    }
}