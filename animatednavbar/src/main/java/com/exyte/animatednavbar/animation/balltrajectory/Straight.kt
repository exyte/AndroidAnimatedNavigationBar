package com.exyte.animatednavbar.animation.balltrajectory

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.ballSize
import com.exyte.animatednavbar.utils.toPxf

/**
 *Describing straight ball animation
 *@param [animationSpec] animation spec of straight ball trajectory
 */

@Stable
class Straight(
    private val animationSpec: AnimationSpec<Offset>
) : BallAnimation {
    @Composable
    override fun animateAsState(targetOffset: Offset, layoutOffset: Offset): State<BallAnimInfo> {
        if (targetOffset.isUnspecified && layoutOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }

        var ballAnimInfo by remember { mutableStateOf(BallAnimInfo()) }

        val density = LocalDensity.current
        val verticalOffset = remember { 2.dp.toPxf(density) }
        val ballSizePx = remember { ballSize.toPxf(density) }

        val offset = animateOffsetAsState(
            targetValue = calculateOffset(
                targetOffset,
                layoutOffset,
                ballSizePx,
                verticalOffset
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

    private fun calculateOffset(
        offset: Offset,
        layoutOffset: Offset,
        ballSizePx: Float,
        verticalOffset: Float
    ) =
        Offset(
            x = offset.x + layoutOffset.x - ballSizePx / 2f,
            y = offset.y + layoutOffset.y - verticalOffset
        )
}