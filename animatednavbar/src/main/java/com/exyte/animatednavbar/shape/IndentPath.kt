package com.exyte.animatednavbar.shape

import android.graphics.PointF
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

class IndentPath(
    private val rect: Rect,
) {
    private val maxX = 110f
    private val maxY = 34f

    private fun translate(x: Float, y: Float): PointF {
        return PointF(
            ((x / maxX) * rect.width) + rect.left,
            ((y / maxY) * rect.height) + rect.top
        )
    }

    fun createPath(): Path {
        val start = translate(x = 0f, y = 0f)
        val middle = translate(x = 55f, y = 34f)
        val end = translate(x = 110f, y = 0f)

        val control1 = translate(x = 23f, y = 0f)
        val control2 = translate(x = 39f, y = 34f)
        val control3 = translate(x = 71f, y = 34f)
        val control4 = translate(x = 87f, y = 0f)

        val path = Path()
        path.moveTo(start.x, start.y)
        path.cubicTo(control1.x, control1.y, control2.x, control2.y, middle.x, middle.y)
        path.cubicTo(control3.x, control3.y, control4.x, control4.y, end.x, end.y)

        return path
    }
}