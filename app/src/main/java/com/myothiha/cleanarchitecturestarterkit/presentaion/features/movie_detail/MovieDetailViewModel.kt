package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.MovieFullDetail
import com.myothiha.domain.usecases.FetchMovieDetailUseCase
import com.myothiha.domain.usecases.UpdateMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 12/03/2024 at 12:01 PM.
 **/
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateMovieUseCase: UpdateMovieUseCase,
    private val movieDetailUseCase: FetchMovieDetailUseCase,

    ) : BaseViewModel() {
    var movieId by mutableIntStateOf(0)
    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        savedStateHandle.get<Int>("id")?.let {
            movieId = it
        }
        retrieveMovieDetail()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSaveMovie -> saveMovieById(event.movieId, event.isLiked)
        }
    }

    private fun retrieveMovieDetail() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                val detail = movieDetailUseCase.execute(params = movieId)
                uiState = uiState.copy(
                    isLoading = false,
                    movieDetail = detail
                )
            }.getOrElse {
                uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))

            }
        }
    }

    private fun saveMovieById(movieId: Int, isLiked: Boolean) {
        viewModelScope.launch {
           // updateMovieUseCase.execute(params = Pair(movieId, isLiked))
        }
    }
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    var movieDetail: MovieFullDetail? = null,
    var errorMessage: String? = null
)

sealed class ScreenUiEvent {
    data class onSaveMovie(val movieId: Int, val isLiked: Boolean) : ScreenUiEvent()
}
