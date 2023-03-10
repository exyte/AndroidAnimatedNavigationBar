package com.exyte.navbar.navbar.animation.balltrajectory

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.utils.toPxf

class Teleport(
    val animationSpec: AnimationSpec<Float>
) : BallAnimation {

    @Composable
    override fun animateAsState(
        targetOffset: Offset,
        layoutShapeInfo: ShapeInfo
    ): State<BallAnimInfo> {
        if (targetOffset.isUnspecified && layoutShapeInfo.layoutOffset.isUnspecified) {
            return derivedStateOf { BallAnimInfo() }
        }
        var from by remember { mutableStateOf(Offset.Zero) }
        var to by remember { mutableStateOf(Offset.Zero) }
        val fraction = remember { Animatable(0f) }

        val density = LocalDensity.current
        val offset by remember(targetOffset, layoutShapeInfo) {
            derivedStateOf {
                mutableStateOf(
                    Offset(
                        x = targetOffset.x + layoutShapeInfo.layoutOffset.x - ballSize.toPxf(density) / 2,
                        y = targetOffset.y + layoutShapeInfo.layoutOffset.y
                    )
                )
            }
        }

        suspend fun setNewAnimationPoints() {
            if(from == to){
                from = offset.value
            } else {
                from = to
            }

            to = offset.value
            fraction.snapTo(0f)
        }

        suspend fun changeToAndFromPointsWhileAnimating() {
            from = to
            to = offset.value
            fraction.snapTo(2f - fraction.value)
        }

        fun changeToAnimationPointWhileAnimating() {
            to = offset.value
        }

        LaunchedEffect(offset) {
            when {
                isExitBallAnimation(fraction.value) -> {
                    changeToAnimationPointWhileAnimating()
                }
                isEnterBallAnimation(fraction.value) -> {
                    changeToAndFromPointsWhileAnimating()
                }
                isAnimationNotRunning(fraction.value) -> {
                    setNewAnimationPoints()
                }
            }
            fraction.animateTo(2f, animationSpec)
        }

        var ballAnimInfo by remember {
            mutableStateOf(BallAnimInfo(scale = 1f, offset = offset.value))
        }

        return remember {
            derivedStateOf {
                ballAnimInfo = if (fraction.value < 1f) {
                    ballAnimInfo.copy(
                        scale = 1f - fraction.value,
                        offset = from
                    )
                } else {
                    ballAnimInfo.copy(
                        scale = fraction.value - 1f,
                        offset = to
                    )
                }
                ballAnimInfo
            }
        }
    }

    private fun isExitBallAnimation(fraction: Float) = fraction > 0f && fraction <= 1f
    private fun isEnterBallAnimation(fraction: Float) = (fraction > 1f) && (fraction < 2f)
    private fun isAnimationNotRunning(fraction: Float) = fraction == 0f || fraction == 2f

}