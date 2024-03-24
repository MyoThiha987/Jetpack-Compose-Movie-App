package com.myothiha.cleanarchitecturestarterkit.presentaion.features.search_movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.navigation.AppDestination
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.LoadingView
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.seemore.MoviesGridView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.ErrorMessage

/**
 * @Author myothiha
 * Created 24/03/2024 at 4:09 PM.
 **/
@Composable
fun SearchMoviesScreen(
    navController: NavController,
    viewModel: SearchMovieViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val uiState = viewModel.uiState
    val uiEvent = viewModel::onEvent
    SearchMoviesContent(
        navController = navController,
        uiState = uiState,
        uiEvent = uiEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesContent(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
) {

    val data = uiState.data?.collectAsLazyPagingItems()

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
                title = { Text(text = stringResource(id = R.string.lbl_favourite)) })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchMovieSection(
                query = uiState.query.orEmpty(),
                modifier = Modifier.padding(padding),
                onQueryChange = {
                    uiEvent(ScreenUiEvent.OnQueryChange(it))
                },
                onClickSearch = {
                    uiEvent(ScreenUiEvent.OnSearchMovie(query = it))
                }
            )

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
                    MoviesGridView(
                        state = state,
                        paddingValues = PaddingValues(horizontal = 16.dp),
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
fun SearchMovieSection(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClickSearch: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Gray.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.onSecondary),
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onClickSearch(query)
                focusManager.clearFocus()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(
                text = "Search Movies",
                style = TextStyle(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            )
        }
    )

}