package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myothiha.cleanarchitecturestarterkit.R

/**
 * @Author myothiha
 * Created 11/03/2024 at 9:19 PM.
 **/

@Composable
fun MovieDetailHeader(
    data: String,
    modifier: Modifier = Modifier,
    state: MovieDetailScreenScrollState = rememberMovieDetailScreenScrollState()
) {
    AnimatedVisibility(
        visible = state.isSheetScrolled.not(),
        enter = fadeIn(
            animationSpec = tween(
                state.sheetScrollOffset.toInt(),
                easing = EaseIn
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                1000,
                easing = EaseOut
            )
        )
    ) {
        Row(
            modifier = modifier
                .height(225.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MovieImageView(
                modifier = Modifier.fillMaxWidth(),
                data = data ,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailHeaderPreview() {
    MovieDetailHeader(
        data ="",
        modifier = Modifier.fillMaxWidth(),
        state = rememberMovieDetailScreenScrollState()
    )
}