package com.exyte.animatednavbar.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import com.exyte.animatednavbar.animation.balltrajectory.BallAnimInfo

fun Modifier.noRippleClickable(
    onClick: () -> Unit
) = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.ballTransform(ballAnimInfo: BallAnimInfo) = this
    .offset {
        IntOffset(
            x = ballAnimInfo.offset.x.toInt(),
            y = ballAnimInfo.offset.y.toInt()
        )
    }
    .graphicsLayer {
        scaleY = ballAnimInfo.scale
        scaleX = ballAnimInfo.scale
        transformOrigin = TransformOrigin(pivotFractionX = 0.5f, 0f)
    }

fun Modifier.rotationWithTopCenterAnchor(degrees: Float) = this
    .graphicsLayer(
        transformOrigin = TransformOrigin(
            pivotFractionX = 0.5f,
            pivotFractionY = 0.1f,
        ),
        rotationZ = degrees
    )