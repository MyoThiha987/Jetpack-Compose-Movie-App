package com.myothiha.cleanarchitecturestarterkit.ui.theme.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * @Author myothiha
 * Created 12/03/2024 at 11:26 AM.
 **/

@Composable
fun Modifier.bouncingClickable(
    onClick: () -> Unit
) = composed {

    val interactionSource = remember { MutableInteractionSource() }


    val isPressed by interactionSource.collectIsPressedAsState()

    val animationTransition = updateTransition(targetState = isPressed, label = "press")

    val scale by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.94f else 1f },
        label = "scale animation"
    )

    val opactiy by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.7f else 1f },
        label = "opactiy animation"
    )

    this
        .graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale
            this.alpha = opactiy
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = true,
            onClick = onClick
        )


}