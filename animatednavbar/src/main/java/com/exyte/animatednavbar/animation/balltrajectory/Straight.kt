package com.exyte.animatednavbar.animation.balltrajectory

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
    override fun animateAsState(targetOffset: Offset): State<BallAnimInfo> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }

        val density = LocalDensity.current
        val verticalOffset = remember { 2.dp.toPxf(density) }
        val ballSizePx = remember { ballSize.toPxf(density) }

        val offset = animateOffsetAsState(
            targetValue = calculateOffset(targetOffset, ballSizePx, verticalOffset),
            animationSpec = animationSpec
        )

        return produceState(
            initialValue = BallAnimInfo(),
            key1 = offset.value
        ) {
            this.value = this.value.copy(offset = offset.value)
        }
    }

    private fun calculateOffset(
        offset: Offset, ballSizePx: Float, verticalOffset: Float
    ) = Offset(
        x = offset.x - ballSizePx / 2f, y = offset.y - verticalOffset
    )
}