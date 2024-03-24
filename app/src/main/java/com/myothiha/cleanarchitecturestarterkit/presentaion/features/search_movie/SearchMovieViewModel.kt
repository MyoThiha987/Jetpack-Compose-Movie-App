package com.myothiha.cleanarchitecturestarterkit.presentaion.features.search_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myothiha.appbase.base.BaseViewModel
import com.myothiha.domain.model.Movie
import com.myothiha.domain.usecases.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author myothiha
 * Created 24/03/2024 at 11:15 PM.
 **/


@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val useCase: SearchMoviesUseCase
) : BaseViewModel() {


    var uiState by mutableStateOf(ScreenUiState())
        private set


    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.OnSearchMovie -> searchMovies(query = event.query)
            is ScreenUiEvent.OnQueryChange -> updateQuery(newQuery = event.newQuery)
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            val data = useCase.execute(params = query).cachedIn(viewModelScope)
            uiState = uiState.copy(
                data = data
            )
        }

    }

    fun updateQuery(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
    }
}

data class ScreenUiState(
    var data: Flow<PagingData<Movie>>? = null,
    var query: String ? = null,
)

sealed class ScreenUiEvent {
    data class OnSearchMovie(val query: String) : ScreenUiEvent()
    data class OnQueryChange(val newQuery: String) : ScreenUiEvent()
}