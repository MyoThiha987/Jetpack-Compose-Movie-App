package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * @Author myothiha
 * Created 19/03/2024 at 3:08 PM.
 **/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleLineText(
    text: String,
    modifier: Modifier = Modifier,
    shouldUseMarquee: Boolean = false,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier.then(if (shouldUseMarquee) Modifier.basicMarquee() else Modifier),
        text = text,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = style
    )
}