package com.myothiha.domain.usecases

import com.myothiha.domain.model.Movie
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:25 PM.
 **/

class MoviesUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<Movie>>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit): List<Movie> {
        TODO("Not yet implemented")
    }
}