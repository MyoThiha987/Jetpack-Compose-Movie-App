package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movie_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieDetailBodySheet
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieDetailHeader
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieTopAppBar
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.rememberMovieDetailScreenScrollState
import kotlin.math.roundToInt

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:42 PM.
 **/

@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState
    val uiEvent = viewModel::onEvent

    MovieDetailScreen(
        navController = navController,
        uiState = uiState,
        uiEvent = uiEvent,
        onSearchClick = onSearchClick
    )

}

@Composable
fun MovieDetailScreen(
    navController: NavController,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberMovieDetailScreenScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(state.screenNestedScrollConnection),
        snackbarHost = {},
        topBar = {
            MovieTopAppBar(
                navController = navController,
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            MovieDetailHeader(
                data = uiState.movieDetail?.movieDetail?.backdropPath.orEmpty(),
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        state.onHeaderPositioned(
                            coordinates.size.height.toFloat() - innerPadding.calculateTopPadding().value,
                        )
                    },
            )
            uiState.movieDetail?.let {
                MovieDetailBodySheet(
                    data = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 188.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(
                                constraints.copy(maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt()),
                            )
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(
                                    0,
                                    0 + (state.sheetScrollOffset / 2).roundToInt(),
                                )
                            }
                        },
                    timetableScreenScrollState = state,
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "${uiState.errorMessage}",
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun HorizontalTextView(text: String, subText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier,
            fontSize = 16.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        )
        Text(
            text = subText,
            modifier = Modifier,
            fontSize = 16.sp,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        )
    }

}
/*@Composable
fun DetailScreen(
    navController: NavController
) {



    *//*var loadingState by remember {
        mutableStateOf(Pair(false, false))
    }

    if (loadingState.first) {
        LoadingDialog(isShowContent = loadingState.second) {
            loadingState = Pair(false, false)
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = {
                loadingState = Pair(true, false)
            }
        ) {

        }

    }*//*
}

@Composable
fun LoadingDialog(
    isShowContent: Boolean,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    onClickBack: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onClickBack()
        },
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        DialogContent(isShowContent = isShowContent)
    }
}

@Composable
fun DialogContent(
    isShowContent: Boolean
) {
    Box(
        modifier = Modifier
            .size(76.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        if (isShowContent) CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            color = Color.Red
        )
        else Text(
            text = "Congratulations",
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}*/

@Preview
@Composable
fun TimetableScreenPreview() {
    MovieDetailScreen(
        navController = rememberNavController(),
        uiState = ScreenUiState(),
        uiEvent = {},
        onSearchClick = {}
    )
}


