package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myothiha.domain.model.Movie

@Composable
fun MovieItemMediumView(
    modifier: Modifier = Modifier,
    data: Movie,
) {
    Column(modifier = modifier) {
        Card {
            MovieImageView(
                modifier = Modifier
                    .width(250.dp)
                    .height(190.dp),
                data = "https://image.tmdb.org/t/p/original/${data.posterPath}"
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .width(239.dp)
                .wrapContentHeight(),
            text = data.originalTitle,
            color = Color.Black,
            maxLines = 1,
            fontSize = 16.sp,
            lineHeight = 18.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItemSmallView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail : ()->Unit
) {
    Column(modifier = modifier) {
        Card {
            MovieImageView(
                modifier = Modifier
                    .width(180.dp)
                    .height(240.dp)
                    .clickable {
                        onClickDetail()
                    },
                data = "https://image.tmdb.org/t/p/original/${data.posterPath}"
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .width(180.dp),
            text = data.originalTitle,
            color = Color.Black,
            fontSize = 18.sp,
            maxLines = 1,
            fontWeight = FontWeight(200)
        )

    }
}

@Composable
fun LazyItemScope.MovieItemLargeView(
    modifier: Modifier = Modifier,
    data: Movie,
) {
    Column(modifier = modifier) {
        Card(shape = RoundedCornerShape(12.dp)) {
            MovieImageView(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(16f / 9f),
                data = "https://image.tmdb.org/t/p/original/${data.posterPath}"
            )
        }
    }
}

@Composable
fun <T> MovieImageView(modifier: Modifier = Modifier, data: T) {
    AsyncImage(
        model = data,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}