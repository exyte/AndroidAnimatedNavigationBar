package com.exyte.navbar.navbar

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset

sealed class BallAnimation {

    @Composable
    abstract fun animateAsState(toOffset: Offset): State<BallAnimInfo>
    data class Parabolic(
        val animationSpec: FiniteAnimationSpec<Float>
    ) : BallAnimation() {
        @Composable
        override fun animateAsState(toOffset: Offset): State<BallAnimInfo> {
            var from by remember { mutableStateOf(Offset.Zero) }
            var to by remember { mutableStateOf(Offset.Zero) }
            val fraction = remember { Animatable(0f) }

            val path = remember { Path() }
            val pathMeasurer = remember { PathMeasure() }
            val pathLength = remember { mutableStateOf(0f) }
            val pos = remember { floatArrayOf(0f, 0f) }
            val tan = remember { floatArrayOf(0f, 0f) }

            var point by remember { mutableStateOf(Offset.Zero) }

            LaunchedEffect(toOffset) {
                if (to != toOffset) {
                    from = to
                    to = toOffset

                    path.reset()
                    path.moveTo(from.x, from.y)
                    path.quadTo(
                        (from.x + to.x) / 2f,
                        from.y - 300f,
                        to.x,
                        to.y
                    )

                    pathMeasurer.setPath(path, false)
                    pathLength.value = pathMeasurer.length

                    fraction.snapTo(0f)
                    fraction.animateTo(1f, animationSpec)
                }
            }

            var ballAnimInfo by
            remember { mutableStateOf(BallAnimInfo(scale = 1f, offset = toOffset)) }

            return remember {
                derivedStateOf {
                    pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
                    point = point.copy(x = pos[0], y = pos[1])
                    ballAnimInfo = ballAnimInfo.copy(offset = point)
                    ballAnimInfo
                }
            }
        }
    }

    data class Teleport(
        val animationSpec: AnimationSpec<Float>
    ) : BallAnimation() {

        @Composable
        override fun animateAsState(toOffset: Offset): State<BallAnimInfo> {
            var from by remember { mutableStateOf(Offset.Zero) }
            var to by remember { mutableStateOf(Offset.Zero) }
            val fraction = remember { Animatable(0f) }

            LaunchedEffect(toOffset) {
                if (to != toOffset) {
                    from = to
                    to = toOffset


                    fraction.snapTo(0f)
                    fraction.animateTo(2f, animationSpec)
                }
            }

            var ballAnimInfo by remember {
                mutableStateOf(BallAnimInfo(scale = 1f, offset = toOffset))
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
    }

    data class Straight(
        val animationSpec: AnimationSpec<Offset>
    ) : BallAnimation() {
        @Composable
        override fun animateAsState(toOffset: Offset): State<BallAnimInfo> {

            var ballAnimInfo by remember {
                mutableStateOf(BallAnimInfo(scale = 1f, offset = toOffset))
            }

            val offset = animateOffsetAsState(
                targetValue = toOffset,
                animationSpec = animationSpec
            )

            return remember {
                derivedStateOf {
                    ballAnimInfo = ballAnimInfo.copy(offset = offset.value)
                    ballAnimInfo
                }
            }
        }
    }
}

data class BallAnimInfo(
    val scale: Float,
    val offset: Offset
)
