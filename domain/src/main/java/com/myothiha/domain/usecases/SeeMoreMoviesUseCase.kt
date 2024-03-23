package com.myothiha.domain.usecases

import androidx.paging.PagingData
import com.myothiha.domain.model.Movie
import com.myothiha.domain.repository.MoviesRepository
import com.myothiha.domain.utils.coroutine.DispatcherProvider
import com.myothiha.domain.utils.coroutine.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 23/03/2024 at 5:07 PM.
 **/
class SeeMoreMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, PagingData<Movie>>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Int): Flow<PagingData<Movie>> {
        return repository.fetchPagingMovies(movieType = params)
    }
}