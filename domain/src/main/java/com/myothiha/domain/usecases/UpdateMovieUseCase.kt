package com.myothiha.domain.usecases

import com.myothiha.domain.repository.MoviesRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 16/03/2024 at 10:59 PM.
 **/
class UpdateMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Triple<Int, Boolean,Int>, Unit>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Triple<Int, Boolean,Int>) {
        repository.updateSavedMovie(movieId = params.first, isLiked = params.second, movieType = params.third)
    }
}