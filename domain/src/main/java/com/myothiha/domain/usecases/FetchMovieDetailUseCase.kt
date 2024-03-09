package com.myothiha.domain.usecases

import com.myothiha.domain.model.MovieDetail
import com.myothiha.domain.model.MovieFullDetail
import com.myothiha.domain.repository.MoviesRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 09/03/2024 at 4:27 PM.
 **/
class FetchMovieDetailUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Int, MovieFullDetail>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Int) : MovieFullDetail{
       return repository.retrieveMovieDetail(movieId = params)
    }
}