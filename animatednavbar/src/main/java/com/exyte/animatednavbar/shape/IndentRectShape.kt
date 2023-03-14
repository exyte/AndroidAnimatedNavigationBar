package com.exyte.animatednavbar.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class IndentRectShape(
    private val indentShapeData: IndentShapeData,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline =
        Outline.Generic(
            Path().addRoundRectWithIndent(size, indentShapeData)
        )

    fun copy(
        cornerRadius: Float = indentShapeData.cornerRadius,
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
): Path {
    val width = size.width
    val height = size.height
    val cornerRadius = if (size.height > indentShapeData.cornerRadius) {
        indentShapeData.cornerRadius
    } else {
        size.height
    }
    val arcRectSize = Size(cornerRadius, cornerRadius)
    val sweepAngleDegrees = 90f

    return apply {

        moveTo(cornerRadius, 0f)

        val xOffset = indentShapeData.xIndent - indentShapeData.width / 2
        if (xOffset > cornerRadius/4) {
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

        lineTo(width - cornerRadius, 0f)
        arcTo(
            rect = Rect(offset = Offset(width - cornerRadius, 0f), size = arcRectSize),
            startAngleDegrees = 270f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(width, height - cornerRadius)
        arcTo(
            rect = Rect(
                offset = Offset(width - cornerRadius, height - cornerRadius),
                size = arcRectSize
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(width - cornerRadius, height)
        arcTo(
            rect = Rect(offset = Offset(0f, height - cornerRadius), size = arcRectSize),
            startAngleDegrees = 90f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        lineTo(0f, cornerRadius)
        arcTo(
            rect = Rect(offset = Offset(0f, 0f), size = arcRectSize),
            startAngleDegrees = 180f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        close()
    }
}