package com.myothiha.domain.usecases

import com.myothiha.domain.repository.MoviesRepository
import com.myothiha.domain.utils.coroutine.CoroutineUseCase
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:25 PM.
 **/

class SyncMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, Unit>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit) {
        repository.syncMovies()
    }
}