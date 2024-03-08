package com.myothiha.domain.usecases

import com.myothiha.domain.model.Movie
import com.myothiha.domain.repository.MoviesRepository
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import com.myothiha.domain.utils.coroutine.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 05/03/2024 at 11:25 PM.
 **/

class CacheMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, List<Movie>>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit): Flow<List<Movie>> {
        return repository.retrieveMovies()
    }
}