package com.myothiha.cleanarchitecturestarterkit.presentaion.features.movies

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myothiha.cleanarchitecturestarterkit.R
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
fun MoviesScreen(
    modifier: Modifier = Modifier,
    uiState: ScreenUiState,
    uiEvent: (ScreenUiEvent) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) LoadingView()
        if (uiState.errorMessage != null) Toast.makeText(
            context,
            uiState.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
        HeaderSection()
        MoviesSection(uiState = uiState)

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesSection(uiState: ScreenUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            CarouselMovieView(data = uiState.upcomingData)
            CategoryNameAndMovies(
                text = "NowPlaying",
                content = {
                    HorizontalItemMovies(data = uiState.nowPlayingData) {
                        MovieItemSmallView(data = it)
                    }
                }
            )
            CategoryNameAndMovies(
                text = "TopRate",
                content = {
                    HorizontalLargeItemMovies(uiState.topRatedData, isFling = true)
                }
            )
            CategoryNameAndMovies(
                text = "Popular",
                content = {
                    HorizontalItemMovies(data = uiState.nowPlayingData) {
                        MovieItemMediumView(data = it)
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryNameAndMovies(
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
fun HorizontalItemMovies(
    data: List<Movie>,
    state: LazyListState = rememberLazyListState(),
    content: @Composable (Movie) -> Unit,

    ) {
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        itemsIndexed(
            items = data,
            key = { index, movie -> movie.id }
        ) { index, it ->
            content(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalLargeItemMovies(
    data: List<Movie>,
    isFling: Boolean,
    state: LazyListState = rememberLazyListState(),

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
        ) { index, it ->
            MovieItemLargeView(data = it)
        }
    }
}


