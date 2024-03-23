package com.myothiha.data.network.mediator

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myothiha.data.di.KtorNetworkModule.pathUrl
import com.myothiha.data.network.dto.MovieDto
import com.myothiha.data.network.response.DataResponse
import com.myothiha.data.network.utils.Constants
import com.myothiha.data.network.utils.Constants.GET_POPULAR
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 23/03/2024 at 4:09 PM.
 **/

private const val STARTING_PAGE_INDEX = 1
private const val LOAD_DELAY_MILLIS = 1000L

class MoviesNetworkPagingSource @Inject constructor(
    private val movieType: Int,
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

        Log.d("TYPE_PAGE","$movieType")

        val movieType = when (movieType) {
            2 -> Constants.GET_NOW_PLAYING
            3 -> Constants.GET_TOP_RATED
            4 -> GET_POPULAR
            else->""
        }
        return runCatching {
            val raw = client.get {
                pathUrl(movieType, page = startKey)
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