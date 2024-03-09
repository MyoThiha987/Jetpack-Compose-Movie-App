package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.myothiha.domain.model.Movie
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

/**
 * @Author myothiha
 * Created 08/03/2024 at 2:35 PM.
 **/


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselMovieView(
    modifier: Modifier = Modifier,
    data: List<Movie>,
    pagerState: PagerState = rememberPagerState(initialPage = 0)
) {
    val images = data.map { it.posterPath }
    //val pagerState = com.google.accompanist.pager.rememberPagerState(initialPage = 0)
    Column(modifier = modifier.padding(top = 16.dp)) {
        com.google.accompanist.pager.HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            count = images.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { currentPage ->
            CarouselMovieItem(currentPage = currentPage, images = images)

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerScope.CarouselMovieItem(currentPage: Int, images: List<String>) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.graphicsLayer {
            val pageOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue
            lerp(start = 0.95f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)).also {
                scaleX = it
                scaleY = it
            }
            alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
            model = "https://image.tmdb.org/t/p/original/${images[currentPage]}",
            contentDescription = "Movie Image"
        )
    }
}

