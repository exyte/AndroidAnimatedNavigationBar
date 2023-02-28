//package com.exyte.navbar.navbar.animation
//
//import android.graphics.Path
//import android.graphics.PathMeasure
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.AnimationSpec
//import androidx.compose.animation.core.tween
//import androidx.compose.runtime.*
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.IntOffset
//import com.exyte.navbar.navbar.animation.shape.IndentShapeAnimation
//import com.exyte.navbar.navbar.ballSize
//import com.exyte.navbar.navbar.toPx

//class ParabolicNavBarAnimation(
//    val cornerRadius: Dp,
//    val layoutOffset: Offset,
//    val animationSpec: AnimationSpec<Float>
//) : BallAnimation, IndentShapeAnimation {
//
//    @Composable
//    fun animateBallAsState(toOffset: Offset): State<Rect> {
//        var from by remember { mutableStateOf(Offset.Zero) }
//        var to by remember { mutableStateOf(Offset.Zero) }
//        val fraction = remember { Animatable(0f) }
//
//        val path = remember { Path() }
//        val pathMeasurer = remember { PathMeasure() }
//        val pathLength = remember { mutableStateOf(0f) }
//        val pos = remember { floatArrayOf(0f, 0f) }
//        val tan = remember { floatArrayOf(0f, 0f) }
//
//        var point by remember { mutableStateOf(IntOffset.Zero) }
//
//        var rect by remember { mutableStateOf(Rect.Zero) }
//
//        LaunchedEffect(toOffset) {
//            if (to != toOffset) {
//                from = to
//                to = toOffset
//
//                path.reset()
//                path.moveTo(from.x, from.y)
//                path.quadTo(
//                    (from.x + to.x) / 2f,
//                    from.y - 300f,
//                    to.x,
//                    to.y
//                )
//
//                pathMeasurer.setPath(path, false)
//                pathLength.value = pathMeasurer.length
//
//                fraction.snapTo(0f)
//                fraction.animateTo(1f, tween(1000))
//            }
//        }
//
//        val ballSizePx = ballSize.toPx()
//
//        return remember {
//            derivedStateOf {
//                pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
//                point = point.copy(x = pos[0].toInt(), y = pos[1].toInt())
//                rect = rect.copy(
//                    point.x.toFloat(),
//                    point.y.toFloat(),
//                    point.x.toFloat() + ballSizePx,
//                    bottom = point.x.toFloat() + ballSizePx
//                )
//                rect
//            }
//        }
//    }
//
//    private val changeIndentHeightAnimation = ChangeIndentHeightAnimation(
//        cornerRadius = cornerRadius,
//        layoutOffset = layoutOffset,
//        animationSpec = animationSpec
//    )
//
//    @Composable
//    override fun animateIndentShape(toOffset: Offset): State<Shape> =
//        changeIndentHeightAnimation.animateIndentShape(toOffset = toOffset)
//
//    @Composable
//    override fun animateBallSizeAsState(): State<Size> {
//        TODO("Not yet implemented")
//    }
//
//    @Composable
//    override fun animateBallOffsetAsState(): State<Offset> {
//        TODO("Not yet implemented")
//    }
//}