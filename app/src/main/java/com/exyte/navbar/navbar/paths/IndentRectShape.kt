package com.exyte.navbar.navbar.paths

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class IndentRectShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline =
        Outline.Generic(
            Path().addRoundRectWithSpecialStartPath(size, 0f, 200f)
        )

}

fun androidx.compose.ui.graphics.Path.addRoundRectWithSpecialStartPath(
    size: Size,
    heightAnimation: Float,
    cornerShape: Float,
): androidx.compose.ui.graphics.Path {
    val width = size.width
    val height = size.height

    return apply {

        moveTo(cornerShape, 0f)
//        addPath()

        lineTo(width - cornerShape, 0f)

        arcTo(
            rect = Rect(
                offset = Offset(width - cornerShape, 0f),
                size = Size(cornerShape, cornerShape)
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(width, height - cornerShape)

        arcTo(
            rect = Rect(
                offset = Offset(width - cornerShape, height - cornerShape),
                size = Size(cornerShape, cornerShape)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(width - cornerShape, height)

        arcTo(
            rect = Rect(
                offset = Offset(0f, height - cornerShape),
                size = Size(cornerShape, cornerShape)
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo(0f, cornerShape)

        arcTo(
            rect = Rect(
                offset = Offset(0f, 0f),
                size = Size(cornerShape, cornerShape)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        close()
    }
}