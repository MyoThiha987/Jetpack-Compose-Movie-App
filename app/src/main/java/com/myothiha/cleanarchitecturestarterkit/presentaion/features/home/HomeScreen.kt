package com.myothiha.cleanarchitecturestarterkit.presentaion.features.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.navigation.AppDestination
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.CarouselMovieView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieItemLargeView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieItemMediumView
import com.myothiha.cleanarchitecturestarterkit.ui.theme.components.MovieItemSmallView
import com.myothiha.domain.model.Movie

/**
 * @Author myothiha
 * Created 07/03/2024 at 11:43 PM.
 **/

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val uiState = viewModel.uiState
    val navigateUiState = viewModel.navigateUiState
    val uiEvent = viewModel::onEvent
    HomeScreen(
        uiState = uiState,
        uiEvent = uiEvent,
        navigateUiState = navigateUiState,
        navController = navController,
        paddingValues = paddingValues
    )


}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: ScreenUiState,
    navigateUiState: NavigateUiState,
    uiEvent: (ScreenUiEvent) -> Unit,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val uiState = uiState

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        HeaderSection()
        if (uiState.isLoading) LoadingView()
        if (uiState.errorMessage != null) {
            LaunchedEffect(key1 = uiState.errorMessage) {
                Toast.makeText(
                    context,
                    uiState.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (uiState.upcomingData.isNotEmpty() && uiState.nowPlayingData.isNotEmpty() && uiState.topRatedData.isNotEmpty() && uiState.popularData.isNotEmpty()) {
            MoviesSection(
                navController = navController,
                uiState = uiState,
                onClickDetail = {
                    navController.navigate(AppDestination.MovieDetailScreen.args(movieId = it))
                },
                onClickSave = { movieId, isLiked, movieType ->
                    uiEvent(
                        ScreenUiEvent.onSaveMovie(
                            movieId = movieId,
                            isLiked = !isLiked,
                            movieType = movieType
                        )
                    )
                }
            )

        }

        /*if (navigateUiState.isReadyToNavigate) {
            navController.navigate("detail")
            uiEvent(ScreenUiEvent.Navigate)
        }*/

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesSection(
    navController: NavController,
    uiState: ScreenUiState,
    modifier: Modifier = Modifier,
    onClickDetail: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit
) {

    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            if (uiState.upcomingData.isNotEmpty())
                CarouselMovieView(data = uiState.upcomingData)
            if (uiState.nowPlayingData.isNotEmpty())
                CategoryAndContent(
                    text = "NowPlaying",
                    content = {
                        HorizontalItemView(
                            arrangement = Arrangement.spacedBy(12.dp),
                            data = uiState.nowPlayingData,
                            content = {
                                MovieItemSmallView(
                                    data = it,
                                    onClickDetail = {
                                        onClickDetail(it)
                                    },
                                    onClickSave = onClickSave

                                )
                            }
                        )
                    }
                )
            if (uiState.topRatedData.isNotEmpty()) CategoryAndContent(
                text = "TopRate",
                content = {
                    HorizontalLargeItemView(
                        uiState.topRatedData,
                        isFling = true,
                        onClickDetail = {
                            onClickDetail(it)
                        }
                    )
                }
            )
            if (uiState.popularData.isNotEmpty()) {
                CategoryAndContent(
                    text = "Popular",
                    content = {
                        HorizontalItemView(
                            arrangement = Arrangement.spacedBy(12.dp),
                            data = uiState.nowPlayingData,
                            content = {
                                MovieItemMediumView(
                                    data = it,
                                    onClickDetail = { onClickDetail(it) })
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryAndContent(
    text: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black
                ),
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = "See more",
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        content()
    }
}

@Composable
fun TitleAndContent(
    title: String,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = verticalArrangement
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black
                ),
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        content()
    }
}


@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = Color.Black,
            modifier = Modifier
                .wrapContentHeight()
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WelcomeView(modifier = Modifier.weight(9.8f))
        IconsRowView()

    }
}

@Composable
fun WelcomeView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Hi,Myo Thiha",
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 21.sp
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome back",
            color = Color.Black,
            fontSize = 20.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun IconsRowView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null
        )
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null
        )
    }
}

@Composable
fun <T> HorizontalItemView(
    data: List<T>,
    arrangement: Arrangement.HorizontalOrVertical,
    alignment: Alignment.Vertical = Alignment.Top,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    content: @Composable (T) -> Unit,
) {

    LazyRow(
        modifier = modifier,
        state = state,
        horizontalArrangement = arrangement,
        verticalAlignment = alignment,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {

        itemsIndexed(
            items = data,
            key = { index, movie -> "${index}${movie}" }
        ) { index, it ->
            content(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalLargeItemView(
    data: List<Movie>,
    isFling: Boolean,
    state: LazyListState = rememberLazyListState(),
    onClickDetail: (Int) -> Unit
) {
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        flingBehavior = if (isFling) rememberSnapFlingBehavior(lazyListState = state) else ScrollableDefaults.flingBehavior()
    ) {
        itemsIndexed(
            items = data,
            key = { index, movie -> movie.id }
        ) { index, movie ->
            MovieItemLargeView(
                onClickDetail = { onClickDetail(movie.id) },
                data = movie
            )
        }
    }
}




