package com.exyte.animatednavbar.animation.indendshape

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.ballSize
import com.exyte.animatednavbar.shape.IndentRectShape
import com.exyte.animatednavbar.shape.IndentShapeData
import com.exyte.animatednavbar.utils.toPxf

@Stable
class StraightIndent(
    private val animationSpec: FiniteAnimationSpec<Float>,
    private val indentWidth: Dp = 50.dp,
    private val indentHeight: Dp = 20.dp,
) : IndentAnimation {

    @Composable
    override fun animateIndentShapeAsState(
        targetOffset: Offset,
        cornerRadius: Float
    ): State<Shape> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(IndentRectShape(IndentShapeData())) }
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