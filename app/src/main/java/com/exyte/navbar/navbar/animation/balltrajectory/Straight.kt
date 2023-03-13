package com.exyte.navbar.navbar.animation.balltrajectory

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.utils.toPxf

/**
 *Describing straight ball animation
 *@param [animationSpec] animation spec of straight ball trajectory
 */

class Straight(
    private val animationSpec: AnimationSpec<Offset>
) : BallAnimation {
    @Composable
    override fun animateAsState(targetOffset: Offset, layoutOffset: Offset): State<BallAnimInfo> {
        if (targetOffset.isUnspecified && layoutOffset.isUnspecified) {
            return derivedStateOf { BallAnimInfo() }
        }

        var ballAnimInfo by remember { mutableStateOf(BallAnimInfo()) }
        val offset = animateOffsetAsState(
            targetValue = calculateOffset(
                targetOffset,
                layoutOffset,
                ballSize.toPxf(LocalDensity.current)
            ),
            animationSpec = animationSpec
        )

        return remember {
            derivedStateOf {
                ballAnimInfo = ballAnimInfo.copy(offset = offset.value)
                ballAnimInfo
            }
        }
    }

    private fun calculateOffset(offset: Offset, layoutOffset: Offset, ballSizePx: Float) =
        Offset(
            x = offset.x + layoutOffset.x - ballSizePx / 2f,
            y = offset.y + layoutOffset.y
        )
}