package com.exyte.navbar.navbar.animation.balltrajectory

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.utils.toPxf

class Parabolic(
    val animationSpec: FiniteAnimationSpec<Float>,
    private val maxHeight: Dp = 100.dp,
) : BallAnimation {
    @Composable
    override fun animateAsState(toOffset: Offset, layoutShapeInfo: ShapeInfo): State<BallAnimInfo> {
        if (toOffset.isUnspecified && layoutShapeInfo.layoutOffset.isUnspecified) {
            return derivedStateOf { BallAnimInfo() }
        }

        var from by remember { mutableStateOf(toOffset) }
        var to by remember { mutableStateOf(toOffset) }
        val fraction = remember { Animatable(0f) }

        val path = remember { Path() }
        val pathMeasurer = remember { PathMeasure() }
        val pathLength = remember { mutableStateOf(0f) }
        val pos = remember { floatArrayOf(0f, 0f) }
        val tan = remember { floatArrayOf(0f, 0f) }

        val density = LocalDensity.current
        val maxHeightPx = remember(maxHeight) { maxHeight.toPxf(density) }

        fun measurePosition() {
            pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
        }

        LaunchedEffect(toOffset) {
            var height = if (to != toOffset) {
                maxHeightPx
            } else {
                startMinHeight
            }

            if (isNotRunning(fraction.value)) {
                from = to
                to = toOffset
            } else {
                measurePosition()
                from = Offset(x = pos[0], y = pos[1])
                to = toOffset
                height = maxHeightPx + pos[1]
            }

            path.createParabolaTrajectory(from = from, to = to, height = height)

            pathMeasurer.setPath(path, false)
            pathLength.value = pathMeasurer.length

            fraction.snapTo(0f)
            fraction.animateTo(1f, animationSpec)
        }

        var ballAnimInfo by remember {
            mutableStateOf(BallAnimInfo())
        }

        return remember(toOffset) {
            derivedStateOf {
                measurePosition()
                ballAnimInfo = ballAnimInfo.copy(
                    offset = calculateNewOffset(pos, layoutShapeInfo, ballSize.toPxf(density))
                )
                ballAnimInfo
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

    private fun calculateNewOffset(pos: FloatArray, layoutShapeInfo: ShapeInfo, ballSizePx: Float) =
        Offset(
            x = pos[0] + layoutShapeInfo.layoutOffset.x - (ballSizePx / 2),
            y = pos[1] + layoutShapeInfo.layoutOffset.y
        )

    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 1f

    companion object {
        const val startMinHeight = -50f
    }
}