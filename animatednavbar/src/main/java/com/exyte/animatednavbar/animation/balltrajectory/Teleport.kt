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
    ): State<BallAnimInfo> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(BallAnimInfo()) }
        }
        var from by remember { mutableStateOf(Offset.Unspecified) }
        var to by remember { mutableStateOf(Offset.Unspecified) }
        val fraction = remember { Animatable(0f) }

        val density = LocalDensity.current
        val verticalOffset = remember { 2.dp.toPxf(density) }

        val offset by remember(targetOffset) {
            derivedStateOf {
                mutableStateOf(
                    Offset(
                        x = targetOffset.x - ballSize.toPxf(density) / 2,
                        y = targetOffset.y - verticalOffset
                    )
                )
            }
        }

        suspend fun setNewAnimationPoints() {
            if (from == Offset.Unspecified) {
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
                isAnimationNotRunning(fraction.value) -> {
                    setNewAnimationPoints()
                }
                isExitBallAnimation(fraction.value) -> {
                    changeToAnimationPointWhileAnimating()
                }
                isEnterBallAnimation(fraction.value) -> {
                    changeToAndFromPointsWhileAnimating()
                }
            }
            fraction.animateTo(2f, animationSpec)
        }

        return produceState(
            initialValue = BallAnimInfo(),
            key1 = fraction.value
        ) {
            this.value = this.value.copy(
                scale = if (fraction.value < 1f) 1f - fraction.value else fraction.value - 1f,
                offset = if (fraction.value < 1f) from else to
            )
        }
    }

    private fun isExitBallAnimation(fraction: Float) = fraction <= 1f
    private fun isEnterBallAnimation(fraction: Float) = fraction > 1f
    private fun isAnimationNotRunning(fraction: Float) = fraction == 0f || fraction == 2f

}