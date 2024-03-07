package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.CacheMoviesUseCase
import com.myothiha.domain.usecases.SyncMoviesUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:39 PM.
 **/
class MoviesViewModel @Inject constructor(
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val cacheMoviesUseCase: CacheMoviesUseCase
) : BaseViewModel() {

    init {
        syncMovies()
    }

    var uiState by mutableStateOf(ScreenUiState())
        private set

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
                uiState = uiState.copy(isLoading = false)
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
                uiState = uiState.copy(isLoading = false, data = it)
            }
        }
    }
}

sealed class ScreenUiEvent {
    data object Retry : ScreenUiEvent()
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    var data: List<Movie> = emptyList(),
    var errorMessage: String? = null
)