package com.myothiha.data.network.mediator

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myothiha.data.di.KtorNetworkModule.pathUrl
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 24/03/2024 at 10:20 PM.
 **/
private const val STARTING_PAGE_INDEX = 1
private const val LOAD_DELAY_MILLIS = 1000L

class SearchMovieNetworkPagingSource @Inject constructor(
    private val query: String,
    private val client: HttpClient
) : PagingSource<Int, MovieDto>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val startKey = params.key ?: STARTING_PAGE_INDEX
        if (startKey != STARTING_PAGE_INDEX) {
            delay(LOAD_DELAY_MILLIS)
        }
        Log.d("SEARCH_PAGING", query)
        return runCatching {
            val raw = client.get {
                pathUrl(Constants.GET_SEARCH, page = startKey)
                parameter("query", query)
            }.body<DataResponse<MovieDto>>()

            val results = raw.data.orEmpty()

            LoadResult.Page(
                data = results,
                prevKey = if (startKey == STARTING_PAGE_INDEX) null else startKey - 1,
                nextKey = if (results.isEmpty()) null else startKey + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }
}