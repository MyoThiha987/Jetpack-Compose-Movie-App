package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.myothiha.cleanarchitecturestarterkit.R
import com.myothiha.cleanarchitecturestarterkit.presentaion.features.home.noRippleClickable
import com.myothiha.domain.model.Movie

@Composable
fun MovieItemMediumView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit

) {
    Column(modifier = modifier) {
        Card(modifier = Modifier.bouncingClickable {
            onClickDetail(data.id)
        }) {
            MovieImageView(
                modifier = Modifier
                    .width(250.dp)
                    .height(190.dp),
                data = data.backdropPath
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(horizontal = 4.dp)
                .width(239.dp)
                .wrapContentHeight(),
            text = data.originalTitle,
            maxLines = 1,
            fontSize = 16.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun MovieItemSmallView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit
) {
    Box(modifier = modifier.width(180.dp)) {
        Column {
            Card(modifier = Modifier
                .bouncingClickable {
                    onClickDetail(data.id)
                }) {
                MovieImageView(
                    modifier = Modifier
                        .width(180.dp)
                        .height(180.dp),
                    data = data.posterPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(horizontal = 4.dp),
                text = data.originalTitle,
                fontSize = 16.sp,
                maxLines = 1,
                lineHeight = 18.sp,
                fontWeight = FontWeight(200)
            )

        }
        val tintColor = if (data.isLiked) Color.Red else Color.White
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .noRippleClickable {
                    onClickSave(data.id, data.isLiked, data.movieType)
                },
            painter = painterResource(id = R.drawable.ic_favourite),
            contentDescription = null,
            tint = tintColor
        )
    }

}

@Composable
fun MovieGridItemView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Card(modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            }) {
                MovieImageView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    data = data.posterPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .width(180.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                text = data.originalTitle,
                fontSize = 18.sp,
                maxLines = 1,
                lineHeight = 21.sp,
                fontWeight = FontWeight(200)
            )

        }
        val tintColor = if (data.isLiked) Color.Red else Color.White
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .noRippleClickable {
                    onClickSave(data.id, data.isLiked, data.movieType)
                },
            painter = painterResource(id = R.drawable.ic_favourite),
            contentDescription = null,
            tint = tintColor
        )
    }

}

@Composable
fun LazyItemScope.MovieItemLargeView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit

) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            MovieImageView(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(16f / 9f),
                data = data.backdropPath
            )
        }
    }
}

@Composable
fun <T> MovieImageView(modifier: Modifier = Modifier, data: T) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context)
            .data("https://image.tmdb.org/t/p/original/${data}")
            .crossfade(true).build(),
        contentDescription = null,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentSize()
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ItemSmallPreview() {
    MovieItemSmallView(
        data = Movie(1, "AA", "", 0.0, "", "", "", 0.0, 0, 0, true),
        onClickDetail = {},
        onClickSave = { a, b, c -> })
}