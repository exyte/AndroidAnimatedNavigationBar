package com.exyte.animatednavbar.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius

class IndentRectShape(
    private val indentShapeData: IndentShapeData,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline =
        Outline.Generic(
            Path().addRoundRectWithIndent(size, indentShapeData, layoutDirection)
        )

    fun copy(
        cornerRadius: ShapeCornerRadius = indentShapeData.cornerRadius,
        xIndent: Float = indentShapeData.xIndent,
        yIndent: Float = indentShapeData.height,
        ballSize: Float = indentShapeData.ballOffset,
    ) = IndentRectShape(
        indentShapeData.copy(
            cornerRadius = cornerRadius,
            xIndent = xIndent,
            height = yIndent,
            ballOffset = ballSize
        )
    )
}

fun Path.addRoundRectWithIndent(
    size: Size,
    indentShapeData: IndentShapeData,
    layoutDirection: LayoutDirection,
): Path {
    val width = size.width
    val height = size.height
    val cornerRadius = indentShapeData.cornerRadius
    val sweepAngleDegrees = 90f

    return apply {

        moveTo(chooseCornerSize(size.height, cornerRadius.topLeft), 0f)

        val xOffset =
            if (layoutDirection == LayoutDirection.Ltr) {
                indentShapeData.xIndent - indentShapeData.width / 2
            } else {
                size.width - indentShapeData.xIndent - indentShapeData.width / 2
            }

        if (xOffset > cornerRadius.topLeft / 4) {
            addPath(
                IndentPath(
                    Rect(
                        Offset(
                            x = xOffset,
                            y = 0f
                        ),
                        Size(indentShapeData.width, indentShapeData.height)
                    )
                ).createPath()
            )
        }

        lineTo(width - cornerRadius.topRight, 0f)
        arcTo(
            rect = Rect(
                offset = Offset(width - cornerRadius.topRight, 0f),
                size = Size(cornerRadius.topRight, cornerRadius.topRight)
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(width, height - cornerRadius.bottomRight)
        arcTo(
            rect = Rect(
                offset = Offset(
                    width - cornerRadius.bottomRight,
                    height - cornerRadius.bottomRight
                ),
                size = Size(cornerRadius.bottomRight, cornerRadius.bottomRight)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(width - cornerRadius.bottomLeft, height)
        arcTo(
            rect = Rect(
                offset = Offset(0f, height - cornerRadius.bottomLeft),
                size = Size(cornerRadius.bottomLeft, cornerRadius.bottomLeft)
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(0f, cornerRadius.topLeft)
        arcTo(
            rect = Rect(
                offset = Offset(0f, 0f),
                size = Size(cornerRadius.topLeft, cornerRadius.topLeft)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        close()
    }
}

fun chooseCornerSize(sizeHeight: Float, cornerRadius: Float): Float {
    return if (sizeHeight > cornerRadius) {
        cornerRadius
    } else {
        sizeHeight
    }
}