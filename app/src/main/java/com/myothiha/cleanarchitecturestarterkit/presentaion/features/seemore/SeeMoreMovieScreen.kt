package com.myothiha.cleanarchitecturestarterkit.presentaion.features.seemore

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.navigation.AppDestination
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.LoadingView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.ErrorMessage
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.LoadingNextPageItem
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieGridItemView
import com.myothiha.domain.model.Movie
import com.myothiha.domain.utils.extension.orZero


/**
 * @Author myothiha
 * Created 23/03/2024 at 5:10 PM.
 **/

@Composable
fun SeeMoreMoviesScreen(
    navController: NavController,
    viewModel: SeeMoreMoviesViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState
    val uiEvent = viewModel::onEvent
    SeeMoreMoviesContent(navController = navController, uiState = uiState, uiEvent = uiEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeMoreMoviesContent(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState()
) {
    val data = uiState.data?.collectAsLazyPagingItems()
    val title = when (uiState.movieType) {
        2 -> R.string.lbl_nowplaying
        3 -> R.string.lbl_toprate
        4 -> R.string.lbl_popular
        else -> R.string.lbl_nowplaying
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                title = { Text(text = stringResource(id = title)) })
        },
        contentColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
    ) {
        when (data?.loadState?.refresh) {
            is LoadState.Loading -> {
                LoadingView()
            }

            is LoadState.Error -> {
                val error = data.loadState.refresh as LoadState.Error
                error.error.localizedMessage?.let { it1 ->
                    ErrorMessage(message = it1) {
                        data.retry()
                    }
                }
            }

            else -> {
                AnimatedVisibility(visible = uiState.isLoading.not()) {
                    MoviesGridView(
                        state = state,
                        paddingValues = it,
                        data = data,
                        onClickDetail = {
                            navController.navigate(
                                AppDestination.MovieDetailScreen.args(
                                    movieId = it
                                )
                            )
                        })
                }
            }
        }


    }

}

@Composable
fun MoviesGridView(
    state: LazyGridState,
    paddingValues: PaddingValues,
    data: LazyPagingItems<Movie>?,
    onClickDetail: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = paddingValues
    ) {

        items(count = data?.itemCount.orZero()) { index ->
            data?.get(index)?.let {
                MovieGridItemView(
                    data = it,
                    onClickDetail = {
                        onClickDetail(it)
                    },
                    onClickSave = { movieId, isLiked, movieType ->
                    })
            }
        }
        data?.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = data.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}