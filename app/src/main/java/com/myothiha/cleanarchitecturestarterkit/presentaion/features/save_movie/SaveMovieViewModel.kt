package com.myothiha.cleanarchitecturestarterkit.presentaion.features.save_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.FetchSavedMovieUseCase
import com.myothiha.domain.usecases.UpdateMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 15/03/2024 at 2:25 PM.
 **/

@HiltViewModel
class SaveMovieViewModel @Inject constructor(
    private val fetchBookmarkMovieUseCase: FetchSavedMovieUseCase,
    private val updateMovieUseCase: UpdateMovieUseCase,
) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        fetchSavedMoviesFromCache()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSaveMovie -> saveMovieById(
                movieId = event.movieId,
                isLiked = event.isLiked,
                movieType = event.movieType
            )
        }
    }

    private fun fetchSavedMoviesFromCache() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            fetchBookmarkMovieUseCase.execute(params = Unit).collectLatest {
                uiState = uiState.copy(
                    isLoading = false, data = it
                )
            }
        }
    }

    private fun saveMovieById(movieId: Int, isLiked: Boolean, movieType: Int) {
        viewModelScope.launch {
            updateMovieUseCase.execute(params = Triple(movieId, isLiked, movieType))
        }
    }
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    val data: List<Movie> = emptyList(),
    var errorMessage: String? = null
)

sealed class ScreenUiEvent {
    data class onSaveMovie(val movieId: Int, val isLiked: Boolean, val movieType: Int) :
        ScreenUiEvent()
}