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
 * Created 24/03/2024 at 4:06 PM.
 **/

class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<String, PagingData<Movie>>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: String): Flow<PagingData<Movie>> {
        return repository.searchMovies(query = params)
    }

}