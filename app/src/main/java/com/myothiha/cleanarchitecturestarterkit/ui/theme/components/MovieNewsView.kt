package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myothiha.cleanarchitecturestarterkit.R
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
                data = data.posterPath
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp)
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
    Box(modifier = modifier) {
        Column {
            Card(modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            }) {
                MovieImageView(
                    modifier = Modifier
                        .width(180.dp)
                        .height(240.dp),
                    data = data.posterPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .width(180.dp),
                text = data.originalTitle,
                fontSize = 18.sp,
                maxLines = 1,
                fontWeight = FontWeight(200)
            )

        }
        val tintColor = if (data.isLiked) Color.Red else Color.White
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .clickable {
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
    Box(modifier = modifier
        .fillMaxWidth()
        //.padding(16.dp)
    ) {
        Column {
            Card(modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            }) {
                MovieImageView(
                    modifier = Modifier
                        .height(190.dp),
                    data = data.posterPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .width(180.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                text = data.originalTitle,
                fontSize = 18.sp,
                maxLines = 1,
                fontWeight = FontWeight(200)
            )

        }
        val tintColor = if (data.isLiked) Color.Red else Color.White
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .clickable {
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
                data = data.posterPath
            )
        }
    }
}

@Composable
fun <T> MovieImageView(modifier: Modifier = Modifier, data: T) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/original/${data}",
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
@Preview(showBackground = true)
fun ItemSmallPreview() {
    MovieItemSmallView(
        data = Movie(1, "AA", "", 0.0, "", "", 0.0, 0, 0, true),
        onClickDetail = {},
        onClickSave = { a, b, c -> })
}