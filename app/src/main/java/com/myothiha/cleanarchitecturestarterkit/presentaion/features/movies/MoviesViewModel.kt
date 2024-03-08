package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.CacheMoviesUseCase
import com.myothiha.domain.usecases.SyncMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:39 PM.
 **/

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val cacheMoviesUseCase: CacheMoviesUseCase
) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        syncMovies()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.Retry -> syncMovies()
        }
    }

    private fun syncMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                syncMoviesUseCase.execute(params = Unit)
                retrieveMovies()
            }
                .getOrElse {
                    uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))
                    retrieveMovies()
                }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            cacheMoviesUseCase.execute(params = Unit).collectLatest {
                val groupedMovies = it.groupBy { it.movieType }
                uiState = uiState.copy(
                    isLoading = false,
                    upcomingData = groupedMovies[1] ?: emptyList(),
                    nowPlayingData = groupedMovies[2] ?: emptyList(),
                    topRatedData = groupedMovies[3] ?: emptyList(),
                    popularData = groupedMovies[4] ?: emptyList()
                )
            }
        }
    }
}

sealed class ScreenUiEvent {
    data object Retry : ScreenUiEvent()
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    val upcomingData: List<Movie> = emptyList(),
    val nowPlayingData: List<Movie> = emptyList(),
    val topRatedData: List<Movie> = emptyList(),
    val popularData: List<Movie> = emptyList(),
    var errorMessage: String? = null
)