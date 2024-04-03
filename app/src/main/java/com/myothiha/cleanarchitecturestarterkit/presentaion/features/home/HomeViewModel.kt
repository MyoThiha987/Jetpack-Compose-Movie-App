package com.myothiha.cleanarchitecturestarterkit.presentaion.features.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.CacheMoviesUseCase
import com.myothiha.domain.usecases.SyncMoviesUseCase
import com.myothiha.domain.usecases.UpdateMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:39 PM.
 **/

@HiltViewModel
class HomeViewModel @Inject constructor(
   val savedStateHandle: SavedStateHandle,
    private val syncMoviesUseCase: SyncMoviesUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase,
    private val cacheMoviesUseCase: CacheMoviesUseCase
) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    var navigateUiState by mutableStateOf(NavigateUiState())
        private set

    init {
        retrieveMovies()
        syncMovies()

    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.Retry -> {}
            is ScreenUiEvent.Navigate -> {
                updateNavigateState()
            }

            is ScreenUiEvent.onSaveMovie -> saveMovieById(
                movieId = event.movieId,
                isLiked = event.isLiked,
                movieType = event.movieType
            )
        }
    }

    private fun saveMovieById(movieId: Int, isLiked: Boolean, movieType: Int) {
        viewModelScope.launch {
            updateMovieUseCase.execute(params = Triple(movieId, isLiked, movieType))
        }
    }

    private fun updateNavigateState() {
        navigateUiState = navigateUiState.copy(isReadyToNavigate = false)
    }

    private fun syncMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                syncMoviesUseCase.execute(params = Unit)
            }.getOrElse {
                uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))
                retrieveMovies()
            }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            cacheMoviesUseCase.execute(params = Unit)
                .collectLatest {
                val upComingData = it.filter { it.movieType == 1 }
                val nowPlayingData = it.filter { it.movieType == 2 }
                val topRatedData = it.filter { it.movieType == 3 }
                val popularData = it.filter { it.movieType == 4 }
                uiState = uiState.copy(
                    isLoading = false,
                    upcomingData = upComingData,
                    nowPlayingData = nowPlayingData,
                    topRatedData = topRatedData,
                    popularData = popularData
                )
                navigateUiState =
                    navigateUiState.copy(isReadyToNavigate = upComingData.isNotEmpty() && nowPlayingData.isNotEmpty() && topRatedData.isNotEmpty() && popularData.isNotEmpty())
            }
        }
    }
}

sealed class ScreenUiEvent {
    data object Retry : ScreenUiEvent()
    data object Navigate : ScreenUiEvent()
    data class onSaveMovie(val movieId: Int, val isLiked: Boolean, val movieType: Int) :
        ScreenUiEvent()
}

@Immutable
data class ScreenUiState(
    var isLoading: Boolean = false,
    val upcomingData: List<Movie> = emptyList(),
    val nowPlayingData: List<Movie> = emptyList(),
    val topRatedData: List<Movie> = emptyList(),
    val popularData: List<Movie> = emptyList(),
    var errorMessage: String? = null
)

data class NavigateUiState(
    var isReadyToNavigate: Boolean = false
)