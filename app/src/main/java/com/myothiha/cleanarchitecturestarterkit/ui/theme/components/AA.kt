package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myothiha.cleanarchitecturestarterkit.R

/**
 * @Author myothiha
 * Created 09/03/2024 at 11:31 PM.
 **/


val headerHeight = 200.dp
val toolbarHeight = 56.dp
val collapseRange = headerHeight - toolbarHeight
val avatarSize = 90.dp
val paddingMedium = 16.dp
val paddingSmall = 8.dp

@Composable
fun Header(
    scrollState: ScrollState,
    collapseRangePx: Float,
    modifier: Modifier,
    showToolbar: State<Boolean>
) {
    AnimatedVisibility(
        visible = !showToolbar.value,
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(600)),
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(headerHeight)
                .graphicsLayer {
                    val collapseFraction = (scrollState.value / collapseRangePx).coerceIn(0f, 1f)
                    val yTranslation = lerp(
                        0.dp,
                        -(headerHeight - toolbarHeight),
                        collapseFraction
                    )
                    translationY = yTranslation.toPx()

                    val blur = lerp(0.dp, 3.dp, collapseFraction)
                    if (blur != 0.dp) {
                        renderEffect = BlurEffect(blur.toPx(), blur.toPx(), TileMode.Decal)
                    }
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Avatar(
    scrollState: ScrollState,
    collapseRangePx: Float,
    paddingPx: Float,
    switch: State<Boolean>,
    avatarZIndex: State<Float>,
    avatarRes: Int
) {
    Box(
        modifier = Modifier
            .zIndex(avatarZIndex.value)
            .graphicsLayer {
                val collapseFraction = (scrollState.value / collapseRangePx).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    1.dp,
                    0.5.dp,
                    collapseFraction
                )

                val yTranslation = lerp(
                    headerHeight - (avatarSize / 2),
                    toolbarHeight - (avatarSize * ((1.dp - scaleXY) / 2.dp)),
                    collapseFraction
                )

                translationY = if (switch.value)
                    (toolbarHeight.toPx() - (avatarSize.toPx() * ((1.dp - scaleXY) / 2.dp)) -
                            (scrollState.value - collapseRange.toPx()))
                else
                    yTranslation.toPx()

                translationX = paddingPx

                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }

    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = "",
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .border(paddingSmall, Color.Black, CircleShape)
        )
    }
}

@Composable
fun Toolbar(
    scrollState: ScrollState,
    collapseRangePx: Float,
    titleHeight: MutableState<Float>,
    avatarSizePx: Float,
    profileNameTopPaddingPx: Float,
    showToolbar: State<Boolean>,
    modifier: Modifier
) {
    val showTitle by remember {
        derivedStateOf {
            scrollState.value >=
                    collapseRangePx + avatarSizePx / 2 + profileNameTopPaddingPx + titleHeight.value
        }
    }

    val title = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.W700)) {
            append("Profile Name")
        }
        append("\n")
        withStyle(style = SpanStyle(fontSize = 16.sp, fontWeight = FontWeight.W400)) {
            append("Tweets")
        }
    }

    AnimatedVisibility(
        visible = showToolbar.value,
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(600)),
        modifier = modifier
    ) {
        TopAppBar(
            navigationIcon = {},
            title = {
                if (showTitle) {
                    Text(text = title, color = Color.Black)
                }
            },
            backgroundColor = Color.Blue,
            elevation = 0.dp
        )
    }
}


@Composable
fun ToolbarActions(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight)
    ) {
        IconAction(
            Modifier.padding(top = paddingMedium, start = paddingMedium),
            Icons.Default.ArrowBack
        )
        Spacer(modifier = Modifier.weight(1f))
        IconAction(
            Modifier.padding(top = paddingMedium, end = paddingMedium),
            Icons.Default.MoreVert
        )
    }
}

@Composable
private fun IconAction(modifier: Modifier, image: ImageVector) {
    IconButton(
        onClick = {},
        modifier = modifier
            .clip(CircleShape)
            .size(32.dp)
            .background(Color.White.copy(alpha = 0.4f))
    ) {
        Icon(
            imageVector = image,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.padding(paddingSmall)
        )
    }
}