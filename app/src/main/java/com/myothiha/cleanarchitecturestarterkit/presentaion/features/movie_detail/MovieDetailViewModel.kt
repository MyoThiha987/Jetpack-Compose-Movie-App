package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.MovieFullDetail
import com.myothiha.domain.usecases.FetchMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 12/03/2024 at 12:01 PM.
 **/
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: FetchMovieDetailUseCase,

    ) : BaseViewModel() {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    init {
        retrieveMovieDetail()
    }

    private fun retrieveMovieDetail() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                val detail = movieDetailUseCase.execute(params = 1096197)
                uiState = uiState.copy(
                    isLoading = false,
                    movieDetail = detail
                )
            }.getOrElse {
                uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))

            }
        }
    }
}

data class ScreenUiState(
    var isLoading: Boolean = false,
    var movieDetail: MovieFullDetail? = null,
    var errorMessage: String? = null
)