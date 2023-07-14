package com.exyte.animatednavbar.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Path

class ArcPath(
    private val size: Size,
    private val fabRadius: Float,
){

    fun createPath(): Path {
        val centerX = size.center.x
        val x0 = centerX - fabRadius * 1.15f
        val y0 = 0f

        // offset of the first control point (top part)
        val topControlX = x0 + fabRadius * .5f
        val topControlY = y0

        // offset of the second control point (bottom part)
        val bottomControlX = x0
        val bottomControlY = y0 + fabRadius

        // first curve
        // set the starting point of the curve (P2)
        val firstCurveStart = Offset(x0, y0)

        // set the end point for the first curve (P3)
        val firstCurveEnd = Offset(centerX, fabRadius * 1f)

        // set the first control point (C1)
        val firstCurveControlPoint1 = Offset(
            x = topControlX,
            y = topControlY
        )

        // set the second control point (C2)
        val firstCurveControlPoint2 = Offset(
            x = bottomControlX,
            y = bottomControlY
        )

        // second curve
        // end of first curve and start of second curve is the same (P3)
        val secondCurveStart = Offset(
            x = firstCurveEnd.x,
            y = firstCurveEnd.y
        )

        // end of the second curve (P4)
        val secondCurveEnd = Offset(
            x = centerX + fabRadius * 1.15f,
            y = 0f
        )

        // set the first control point of second curve (C4)
        val secondCurveControlPoint1 = Offset(
            x = secondCurveStart.x + fabRadius,
            y = bottomControlY
        )

        // set the second control point (C3)
        val secondCurveControlPoint2 = Offset(
            x = secondCurveEnd.x - fabRadius / 2,
            y = topControlY
        )

        val path = Path()

        path.lineTo(x = firstCurveStart.x, y = firstCurveStart.y)

        // bezier curve with (P2, C1, C2, P3)
        path.cubicTo(
            x1 = firstCurveControlPoint1.x,
            y1 = firstCurveControlPoint1.y,
            x2 = firstCurveControlPoint2.x,
            y2 = firstCurveControlPoint2.y,
            x3 = firstCurveEnd.x,
            y3 = firstCurveEnd.y
        )

        // bezier curve with (P3, C4, C3, P4)
        path.cubicTo(
            x1 = secondCurveControlPoint1.x,
            y1 = secondCurveControlPoint1.y,
            x2 = secondCurveControlPoint2.x,
            y2 = secondCurveControlPoint2.y,
            x3 = secondCurveEnd.x,
            y3 = secondCurveEnd.y
        )
        return path
    }
}