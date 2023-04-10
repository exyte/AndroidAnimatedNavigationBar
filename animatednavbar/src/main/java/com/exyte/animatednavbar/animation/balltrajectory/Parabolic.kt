package com.exyte.animatednavbar.animation.balltrajectory

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.ballSize
import com.exyte.animatednavbar.utils.toPxf

/**
 *Describing parabola ball animation
 *@param [animationSpec] animation spec of parabolic ball trajectory
 *@param [maxHeight] max height of parabola trajectory
 */

@Stable
class Parabolic(
    private val animationSpec: FiniteAnimationSpec<Float> = spring(),
    private val maxHeight: Dp = 100.dp,
) : BallAnimation {
    @Composable
    override fun animateAsState(targetOffset: Offset): State<BallAnimInfo> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }

        var from by remember { mutableStateOf(targetOffset) }
        var to by remember { mutableStateOf(targetOffset) }
        val fraction = remember { Animatable(0f) }

        val path = remember { Path() }
        val pathMeasurer = remember { PathMeasure() }
        val pathLength = remember { mutableStateOf(0f) }
        val pos = remember { floatArrayOf(Float.MAX_VALUE, Float.MAX_VALUE) }
        val tan = remember { floatArrayOf(0f, 0f) }

        val density = LocalDensity.current
        val maxHeightPx = remember(maxHeight) { maxHeight.toPxf(density) }

        fun measurePosition() {
            pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
        }

        LaunchedEffect(targetOffset) {
            var height = if (to != targetOffset) {
                maxHeightPx
            } else {
                startMinHeight
            }

            if (isNotRunning(fraction.value)) {
                from = to
                to = targetOffset
            } else {
                measurePosition()
                from = Offset(x = pos[0], y = pos[1])
                to = targetOffset
                height = maxHeightPx + pos[1]
            }

            path.createParabolaTrajectory(from = from, to = to, height = height)

            pathMeasurer.setPath(path, false)
            pathLength.value = pathMeasurer.length

            fraction.snapTo(0f)
            fraction.animateTo(1f, animationSpec)
        }

        val verticalOffset = remember { 2.dp.toPxf(density) }
        val ballSizePx = remember { ballSize.toPxf(density) }

        return produceState(
            initialValue = BallAnimInfo(),
            key1 = pos,
            key2 = ballSizePx,
            key3 = fraction.value,
        ) {
            measurePosition()
            if (pos[0] == Float.MAX_VALUE) {
                BallAnimInfo()
            } else {
                this.value = this.value.copy(
                    offset = calculateNewOffset(
                        pos,
                        ballSizePx,
                        verticalOffset
                    )
                )
            }
        }
    }

    private fun Path.createParabolaTrajectory(from: Offset, to: Offset, height: Float) {
        reset()
        moveTo(from.x, from.y)
        quadTo(
            (from.x + to.x) / 2f,
            from.y - height,
            to.x,
            to.y
        )
    }

    private fun calculateNewOffset(
        pos: FloatArray,
        ballSizePx: Float,
        verticalOffset: Float
    ) = Offset(
        x = pos[0] - (ballSizePx / 2),
        y = pos[1] - verticalOffset
    )

    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 1f

    companion object {
        const val startMinHeight = -50f
    }
}