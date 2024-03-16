package com.myothiha.cleanarchitecturestarterkit.presentaion.features.save_movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.LoadingView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieGridItemView

/**
 * @Author myothiha
 * Created 13/03/2024 at 5:45 PM.
 **/

@Composable
fun SaveMovieScreen(
    navController: NavController,
    viewModel: SaveMovieViewModel,
    paddingValues: PaddingValues
) {

    val uiState = viewModel.uiState
    val uiEvent = viewModel::onEvent
    SaveMovieScreen(
        navController = navController,
        uiState = uiState,
        uiEvent = uiEvent,
        paddingValues = paddingValues
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveMovieScreen(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    paddingValues: PaddingValues
) {
    if (uiState.isLoading) LoadingView()
    if (uiState.data.isNotEmpty()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            topBar = {
                TopAppBar(title = { Text(text = "Favourite Movies")})
            },
            contentColor = Color.Black.copy(alpha = 0.5f),
        ) {
            LazyVerticalGrid(
                modifier = Modifier,
                state = rememberLazyGridState(),
                columns = GridCells.Fixed(2),
                contentPadding = it,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = uiState.data, key = { movie -> movie.id }) {
                    MovieGridItemView(
                        data = it,
                        onClickDetail = {},
                        onClickSave = { id, isLiked, movieType ->
                            uiEvent(
                                ScreenUiEvent.onSaveMovie(
                                    movieId = id,
                                    isLiked = !isLiked,
                                    movieType = movieType
                                )
                            )

                        }
                    )
                }

            }
        }

    }


}