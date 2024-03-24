package com.myothiha.data.network.datasources

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.data.di.KtorNetworkModule.pathUrl
import com.myothiha.data.network.dto.CreditResponse
import com.myothiha.data.network.dto.MovieDetailResponse
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.mediator.MoviesNetworkPagingSource
import com.myothiha.data.network.mediator.SearchMovieNetworkPagingSource
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 06/03/2024 at 1:50 AM.
 **/

private const val ITEMS_PER_PAGE = 10

class MoviesNetworkDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : MoviesNetworkDataSource {
    override suspend fun fetchNowPlayingMovies(page: Int): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_NOW_PLAYING, page = page)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchTopRatedMovies(page: Int): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_TOP_RATED, page = page)
        }.body<DataResponse<MovieDto>>()
        return data.data.orEmpty()
    }

    override suspend fun fetchPopularMovies(page: Int): List<MovieDto> {
        val data = client.get {
            pathUrl(Constants.GET_POPULAR, page = page)
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
            pathUrl("movie/${movieId}${Constants.GET_CREDITS}")
        }.body<CreditResponse>()
        return data
    }

    override fun fetchPagingMovies(movieType: Int): Flow<PagingData<MovieDto>> {
        return Pager(
            config =
            PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesNetworkPagingSource(
                    movieType = movieType,
                    client = client
                )
            }
        ).flow
    }
    override fun searchMovies(query: String): Flow<PagingData<MovieDto>> {
        return Pager(
            config =
            PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                initialLoadSize = ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                Log.d("SEARCH_DS",query)
                SearchMovieNetworkPagingSource(
                    query = query,
                    client = client
                )
            }
        ).flow
    }
}