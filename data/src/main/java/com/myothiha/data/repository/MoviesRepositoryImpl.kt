package com.myothiha.data.repository

import com.myothiha.data.datasources.MoviesCacheDataSource
import com.myothiha.data.datasources.MoviesNetworkDataSource
import com.myothiha.data.network.dto.toDomain
import com.myothiha.data.network.dto.toEntity
import com.myothiha.data.network.utils.Constants.NOW_PLAYING
import com.myothiha.data.network.utils.Constants.POPULAR
import com.myothiha.data.network.utils.Constants.TOP_RATED
import com.myothiha.data.network.utils.Constants.UPCOMING
import com.myothiha.domain.model.Movie
import com.myothiha.domain.model.MovieFullDetail
import com.myothiha.domain.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:21 PM.
 **/

class MoviesRepositoryImpl @Inject constructor(
    private val cacheDataSource: MoviesCacheDataSource,
    private val dataSource: MoviesNetworkDataSource
) : MoviesRepository {
    override suspend fun syncMovies() {
        val upComingMoviesMovies = dataSource.fetchUpcomingMovies()
        cacheDataSource.saveMovies(
            data = upComingMoviesMovies.map { it.toEntity(movieType = UPCOMING) },
            movieType = UPCOMING
        )

        val nowPlayingMovies = dataSource.fetchNowPlayingMovies()
        cacheDataSource.saveMovies(
            data = nowPlayingMovies.map { it.toEntity(movieType = NOW_PLAYING) },
            movieType = NOW_PLAYING
        )

        val topRatedMovies = dataSource.fetchTopRatedMovies()
        cacheDataSource.saveMovies(
            data = topRatedMovies.map { it.toEntity(movieType = TOP_RATED) },
            movieType = TOP_RATED
        )

        val popularMovies = dataSource.fetchPopularMovies()
        cacheDataSource.saveMovies(
            data = popularMovies.map { it.toEntity(movieType = POPULAR) },
            movieType = POPULAR
        )
    }

    override fun retrieveMovies(): Flow<List<Movie>> {
        return cacheDataSource.retrieveCacheMovies()
    }

    override suspend fun retrieveMovieDetail(movieId: Int): MovieFullDetail {
        return coroutineScope {
            val detail = async { dataSource.fetchMovieDetail(movieId = movieId).toDomain() }.await()
            val credit = async { dataSource.fetchCredits(movieId = movieId).toDomain() }.await()
            MovieFullDetail(
                movieDetail = detail,
                credit = credit
            )
        }

    }

    override suspend fun updateSavedMovie(movieId: Int, isLiked: Boolean, movieType: Int) {
        cacheDataSource.updateSaveMovie(movieId = movieId, isLiked = isLiked, movieType = movieType)
    }
}