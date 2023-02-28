package com.exyte.navbar.navbar.paths

import android.graphics.PointF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path


class IndentPath(
    val rect: androidx.compose.ui.geometry.Rect,
) {
    val maxX = 110f
    val maxY = 34f

    private fun translate(x: Float, y: Float): PointF {
        return PointF(((x / maxX) * rect.width) + rect.left, ((y / maxY) * rect.height) + rect.top)
    }

    fun createPath(): Path {
        val t1 = translate(x = 0f, y = 0f)
        val t2 = translate(x = 55f, y = 34f)
        val t3 = translate(x = 110f, y = 0f)

        val c1 = translate(x = 23f, y = 0f)
        val c2 = translate(x = 39f, y = 34f)
        val c3 = translate(x = 71f, y = 34f)
        val c4 = translate(x = 87f, y = 0f)

        val path = Path()
        path.moveTo(t1.x, t1.y)
//        path.addRect(
//            Rect(
//                offset = Offset(t1.x, t1.y),
//                size = Size(1f, 10f)
//            )
//        )
        path.cubicTo(c1.x, c1.y, c2.x, c2.y, t2.x, t2.y)
//        path.addRect(
//            Rect(
//                offset = Offset(t2.x, t2.y),
//                size = Size(1f, 10f)
//            )
//        )
        path.cubicTo(c3.x, c3.y, c4.x, c4.y, t3.x, t3.y)
//        path.addRect(
//            Rect(
//                offset = Offset(t3.x, t3.y),
//                size = Size(1f, 10f)
//            )
//        )
        return path

    }
}