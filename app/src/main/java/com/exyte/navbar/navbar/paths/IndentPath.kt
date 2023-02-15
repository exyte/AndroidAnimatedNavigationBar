package com.exyte.navbar.navbar.paths

import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF


class IndentPath(
    val rect: androidx.compose.ui.geometry.Rect
) : Path() {
    val maxX = 55f
    val maxY = 17f

    fun translate(x: Float, y: Float): PointF {
        return PointF(x / maxX * rect.width + rect.left, y / maxY * rect.height + rect.top)
    }

    fun createPath(): Path {
        val t1 = translate(x = 0f, y = 0f)
        val t2 = translate(x = 27.5f, y = 17f)
        val t3 = translate(x = 55f, y = 0f)

        val c1 = translate(x = 11.5f, y = 0f)
        val c2 = translate(x = 19.5f, y = 17f)
        val c3 = translate(x = 35.5f, y = 17f)
        val c4 = translate(x = 43.5f, y = 0f)

        val path = Path()
        path.moveTo(t1.x, t1.y)
        path.cubicTo(c1.x, c1.y, c2.x, c2.y, t2.x, t2.y)
        path.cubicTo(c3.x, c3.y, c4.x, c4.y, t3.x, t3.y)
        return path

    }
}