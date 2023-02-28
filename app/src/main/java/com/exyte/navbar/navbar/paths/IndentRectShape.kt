package com.exyte.navbar.navbar.paths

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.toPxf

data class IndentShapeData(
    val xIndent: Float,
    val height: Float,
    val width: Float,
    val cornerRadius: Float,
    val ballSize: Float,
)

class IndentRectShape(
    private val indentShapeData: IndentShapeData,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline =
        Outline.Generic(
            Path().addRoundRectWithSpecialStartPath(size, indentShapeData)
        )

    fun copy(
        cornerRadius: Float = indentShapeData.cornerRadius,
        xIndent: Float = indentShapeData.xIndent,
        yIndent: Float = indentShapeData.height,
        ballSize: Float = indentShapeData.ballSize,
    ) = IndentRectShape(
        indentShapeData.copy(
            cornerRadius = cornerRadius,
            xIndent = xIndent,
            height = yIndent,
            ballSize = ballSize
        )
    )
}

fun Path.addRoundRectWithSpecialStartPath(
    size: Size,
    indentShapeData: IndentShapeData,
): Path {
    val width = size.width
    val height = size.height
    val cornerShape = if (size.height > indentShapeData.cornerRadius) {
        indentShapeData.cornerRadius
    } else {
        size.height
    }

    return apply {

        moveTo(cornerShape, 0f)

//        addRect(
//            Rect(
//                offset = Offset(
//                    indentShapeData.xIndent - indentShapeData.width / 2 - ballSize.value - 100f, 0f
//                ),
//                size = Size(5f, 5f)
//            )
//        )

        addPath(
            IndentPath(
                Rect(
                    Offset(
                        x = indentShapeData.xIndent - indentShapeData.width/2,
                        y = 0f
                    ),
                    Size(indentShapeData.width, indentShapeData.height)
                )
            ).createPath()
        )

//        addRect(
//            Rect(
//                offset = Offset(indentShapeData.xIndent, 0f),
//                size = Size(1f, 70f)
//            )
//        )

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