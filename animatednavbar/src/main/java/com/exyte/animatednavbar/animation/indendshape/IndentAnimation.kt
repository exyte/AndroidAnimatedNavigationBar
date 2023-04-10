package com.exyte.animatednavbar.animation.indendshape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.exyte.animatednavbar.utils.toPxf

/**
 * defining indent animation, that is above selected item.
 */

interface IndentAnimation {

    /**
     *@param [targetOffset] target offset
     *@param [shapeCornerRadius] corner radius of the navBar layout
     */
    @Composable
    fun animateIndentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius
    ): State<Shape>
}

data class ShapeCornerRadius(
    val topLeft: Float,
    val topRight: Float,
    val bottomRight: Float,
    val bottomLeft: Float,
)

fun shapeCornerRadius(cornerRadius: Float) =
    ShapeCornerRadius(
        topLeft = cornerRadius,
        topRight = cornerRadius,
        bottomRight = cornerRadius,
        bottomLeft = cornerRadius
    )

@Composable
fun shapeCornerRadius(cornerRadius: Dp) =
    ShapeCornerRadius(
        topLeft = cornerRadius.toPxf(),
        topRight = cornerRadius.toPxf(),
        bottomRight = cornerRadius.toPxf(),
        bottomLeft = cornerRadius.toPxf()
    )

@Composable
fun shapeCornerRadius(
    topLeft: Dp,
    topRight: Dp,
    bottomRight: Dp,
    bottomLeft: Dp
) = ShapeCornerRadius(
    topLeft = topLeft.toPxf(),
    topRight = topRight.toPxf(),
    bottomRight = bottomRight.toPxf(),
    bottomLeft = bottomLeft.toPxf()
)