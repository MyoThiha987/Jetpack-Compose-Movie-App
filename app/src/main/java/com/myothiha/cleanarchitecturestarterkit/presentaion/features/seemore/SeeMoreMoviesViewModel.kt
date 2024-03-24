package com.myothiha.cleanarchitecturestarterkit.presentaion.features.seemore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.SeeMoreMoviesUseCase
import com.myothiha.domain.usecases.UpdateMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 23/03/2024 at 5:10 PM.
 **/
@HiltViewModel
class SeeMoreMoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateMovieUseCase: UpdateMovieUseCase,
    private val seeMoreMoviesUseCase: SeeMoreMoviesUseCase
) : BaseViewModel() {

    var movieType by mutableIntStateOf(0)
    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        savedStateHandle.get<Int>("movieType")?.let {
            movieType = it
        }
        fetchSeeMoreMovies()
    }

    fun onEvent(event: ScreenUiEvent){
        when(event){
            is ScreenUiEvent.onSaveMovie -> saveMovieById(
                movieId = event.movieId,
                isLiked = event.isLiked,
                movieType = event.movieType
            )
        }
    }

    private fun fetchSeeMoreMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val data = seeMoreMoviesUseCase.provide(params = movieType).cachedIn(viewModelScope)
            uiState = uiState.copy(
                isLoading = false,
                movieType = movieType,
                data = data
            )
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
    var data: Flow<PagingData<Movie>>? = null,
    var movieType: Int = 0,
    var errorMessage: String? = null
)

sealed class ScreenUiEvent{
    data class onSaveMovie(val movieId: Int, val isLiked: Boolean, val movieType: Int) :
        ScreenUiEvent()
}

