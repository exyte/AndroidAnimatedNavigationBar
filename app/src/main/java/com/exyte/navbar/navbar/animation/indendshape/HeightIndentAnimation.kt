package com.exyte.navbar.navbar.animation.indendshape

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.shape.IndentRectShape
import com.exyte.navbar.navbar.shape.IndentShapeData
import com.exyte.navbar.navbar.utils.lerp
import com.exyte.navbar.navbar.utils.toPxf

data class Height(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val indentWidth: Dp = 50.dp,
    override val indentHeight: Dp = 20.dp,
) : IndentAnimation(animationSpec, indentHeight, indentWidth) {

    @Composable
    override fun animateIndentShapeAsState(
        targetOffset: Offset,
        cornerRadius: Float
    ): State<Shape> {
        if (targetOffset.isUnspecified) {
            return derivedStateOf { IndentRectShape(IndentShapeData()) }
        }

        val fraction = remember { Animatable(0f) }
        var to by remember { mutableStateOf(Offset.Zero) }
        var from by remember { mutableStateOf(Offset.Zero) }

        val density = LocalDensity.current
        var shape by remember {
            mutableStateOf(
                IndentRectShape(
                    indentShapeData = IndentShapeData(
                        ballOffset = ballSize.toPxf(density) / 2f,
                        width = indentWidth.toPxf(density),
                    )
                )
            )
        }

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
                isExitIndentAnimating(fraction.value) -> {
                    changeToAnimationPointWhileAnimating()
                }
                isEnterIndentAnimating(fraction.value) -> {
                    changeToAndFromPointsWhileAnimating()
                }
                isNotRunning(fraction.value) -> {
                    setNewAnimationPoints()
                }
            }
            fraction.animateTo(2f, animationSpec)
        }

        var shapeIndentHeight: Float
        var xIndent: Float

        return remember(cornerRadius) {
            derivedStateOf {
                if (fraction.value <= 1f) {
                    shapeIndentHeight = lerp(indentHeight.toPxf(density), 0f, fraction.value)
                    xIndent = from.x
                    Log.e(
                        "fn;erk",
                        "1fraction: ${fraction.value} shapeIndentHeight ${shapeIndentHeight}"
                    )
                } else {
                    shapeIndentHeight = lerp(0f, indentHeight.toPxf(density), fraction.value - 1f)
                    xIndent = to.x
                    Log.e(
                        "fn;erk",
                        "2fraction: ${fraction.value} shapeIndentHeight ${shapeIndentHeight}"
                    )
                }
                shape = shape.copy(
                    xIndent = xIndent,
                    cornerRadius = cornerRadius,
                    yIndent = shapeIndentHeight,
                )
                shape
            }
        }
    }

    private fun isExitIndentAnimating(fraction: Float) = (fraction > 0f) && (fraction <= 1f)
    private fun isEnterIndentAnimating(fraction: Float) = (fraction > 1f) && (fraction < 2f)
    private fun isNotRunning(fraction: Float) = fraction == 0f || fraction == 2f
}