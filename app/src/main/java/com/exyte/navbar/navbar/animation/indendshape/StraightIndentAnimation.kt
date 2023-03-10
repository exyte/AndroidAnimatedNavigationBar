package com.exyte.navbar.navbar.animation.indendshape

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
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
import com.exyte.navbar.navbar.utils.toPxf

class Straight(
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

        val density = LocalDensity.current
        var shape by remember {
            mutableStateOf(
                IndentRectShape(
                    indentShapeData = IndentShapeData(
                        xIndent = targetOffset.x,
                        height = indentHeight.toPxf(density),
                        ballOffset = ballSize.toPxf(density) / 2f,
                        width = indentWidth.toPxf(density),
                        cornerRadius = cornerRadius
                    )
                )
            )
        }

        val position = animateFloatAsState(
            targetValue = targetOffset.x,
            animationSpec = animationSpec
        )

        return remember(cornerRadius) {
            derivedStateOf {
                shape = shape.copy(xIndent = position.value, cornerRadius = cornerRadius)
                shape
            }
        }
    }
}