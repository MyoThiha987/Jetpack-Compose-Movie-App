package com.myothiha.cleanarchitecturestarterkit.presentaion.features.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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

@OptIn(ExperimentalMaterial3Api::class)
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
        HeaderSection(onClickSearch = {
            navController.navigate(AppDestination.SearchMovieScreen.route)
        })
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
                },
                onClickSeeMore = {
                    navController.navigate(AppDestination.SeeMoreMoviesScreen.args(movieType = it))
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
    onClickSave: (Int, Boolean, Int) -> Unit,
    onClickSeeMore: (Int) -> Unit
) {

    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            if (uiState.upcomingData.isNotEmpty())
                CarouselMovieView(data = uiState.upcomingData)
            if (uiState.nowPlayingData.isNotEmpty())
                CategoryAndContent(
                    text = stringResource(id = R.string.lbl_nowplaying),
                    movieType = 2,
                    onClickSeeMore = {
                        onClickSeeMore(it)
                    },
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
                text = stringResource(id = R.string.lbl_toprate),
                movieType = 3,
                onClickSeeMore = {
                    onClickSeeMore(it)
                },
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
                    text = stringResource(id = R.string.lbl_popular),
                    movieType = 4,
                    onClickSeeMore = {
                        onClickSeeMore(it)
                    },
                    content = {
                        HorizontalItemView(
                            arrangement = Arrangement.spacedBy(16.dp),
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
    movieType: Int,
    onClickSeeMore: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.lbl_seemore),
                modifier = Modifier
                    .noRippleClickable { onClickSeeMore(movieType) }
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
                style = MaterialTheme.typography.titleLarge,
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
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSection(modifier: Modifier = Modifier, onClickSearch: () -> Unit) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WelcomeView(modifier = Modifier.weight(9.8f))
        IconsRowView(onClickSearch = onClickSearch)

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
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            fontSize = 20.sp,
            text = "Hi,Myo Thiha",
            lineHeight = 21.sp
        )
    }
}

@Composable
fun IconsRowView(
    onClickSearch: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        androidx.compose.material3.Icon(
            modifier = Modifier
                .size(32.dp)
                .noRippleClickable {
                    onClickSearch()
                },
            painter = painterResource(id = R.drawable.ic_search),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )
        androidx.compose.material3.Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.ic_notification),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
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

@Composable
inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}




