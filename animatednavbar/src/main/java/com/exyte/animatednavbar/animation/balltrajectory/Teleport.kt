package com.exyte.animatednavbar.animation.balltrajectory

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.ballSize
import com.exyte.animatednavbar.utils.toPxf

/**
 *Describing teleport ball animation. Ball disappears in old location and reappears in the new one
 *@param [animationSpec] animation spec of teleport ball process
 */
@Stable
class Teleport(
    private val animationSpec: AnimationSpec<Float> = spring()
) : BallAnimation {

    @Composable
    override fun animateAsState(
        targetOffset: Offset,
        layoutOffset: Offset
    ): State<BallAnimInfo> {
        if (targetOffset.isUnspecified && layoutOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }
        var from by remember { mutableStateOf(Offset.Zero) }
        var to by remember { mutableStateOf(Offset.Zero) }
        val fraction = remember { Animatable(0f) }

        val density = LocalDensity.current
        val verticalOffset = remember { 2.dp.toPxf(density) }

        val offset by remember(targetOffset, layoutOffset) {
            derivedStateOf {
                mutableStateOf(
                    Offset(
                        x = targetOffset.x + layoutOffset.x - ballSize.toPxf(density) / 2,
                        y = targetOffset.y + layoutOffset.y - verticalOffset
                    )
                )
            }
        }

        suspend fun setNewAnimationPoints() {
            if (from == Offset.Zero) {
                from = offset.value
                fraction.snapTo(1f)
            } else {
                from = to
                fraction.snapTo(0f)
            }
            to = offset.value
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