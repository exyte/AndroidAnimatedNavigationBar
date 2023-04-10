package com.exyte.animatednavbar.animation.indendshape

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.ballSize
import com.exyte.animatednavbar.shape.IndentRectShape
import com.exyte.animatednavbar.shape.IndentShapeData
import com.exyte.animatednavbar.utils.lerp
import com.exyte.animatednavbar.utils.toPxf

@Stable
class Height(
    private val animationSpec: FiniteAnimationSpec<Float>,
    private val indentWidth: Dp = 50.dp,
    private val indentHeight: Dp = 10.dp,
) : IndentAnimation {

    @Composable
    override fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius
    ): State<Shape> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(IndentRectShape(IndentShapeData())) }
        }

        val fraction = remember { Animatable(0f) }
        var to by remember { mutableStateOf(Offset.Zero) }
        var from by remember { mutableStateOf(Offset.Zero) }

        val density = LocalDensity.current

        suspend fun setNewAnimationPoints() {
            from = to
            to = targetOffset
            fraction.snapTo(0f)
        }

        suspend fun changeToAndFromPointsWhileAnimating() {
            from = to
            to = targetOffset
            fraction.snapTo(2f - fraction.value)
        }

        fun changeToAnimationPointWhileAnimating() {
            to = targetOffset
        }

        LaunchedEffect(targetOffset) {
            when {
                isNotRunning(fraction.value) -> {
                    setNewAnimationPoints()
                }

                isExitIndentAnimating(fraction.value) -> {
                    changeToAnimationPointWhileAnimating()
                }

                isEnterIndentAnimating(fraction.value) -> {
                    changeToAndFromPointsWhileAnimating()
                }
            }
            fraction.animateTo(2f, animationSpec)
        }

        return produceState(
            initialValue = IndentRectShape(
                indentShapeData = IndentShapeData(
                    ballOffset = ballSize.toPxf(density) / 2f,
                    width = indentWidth.toPxf(density),
                )
            ),
            key1 = fraction.value,
            key2 = shapeCornerRadius
        ) {
            this.value = this.value.copy(
                yIndent = calculateYIndent(fraction.value,density),
                xIndent = if (fraction.value <= 1f) from.x else to.x,
                cornerRadius = shapeCornerRadius
            )
        }
    }

    private fun calculateYIndent(fraction: Float, density: Density): Float {
        return if (fraction <= 1f) {
            lerp(indentHeight.toPxf(density), 0f, fraction)
        } else {
            lerp(0f, indentHeight.toPxf(density), fraction - 1f)
        }
    }

    private fun isExitIndentAnimating(fraction: Float) = (fraction <= 1f)
    private fun isEnterIndentAnimating(fraction: Float) = (fraction > 1f)
    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 2f
}