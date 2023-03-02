//package com.exyte.navbar.navbar.animation.shape
//
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.AnimationSpec
//import androidx.compose.animation.core.tween
//import androidx.compose.runtime.*
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.exyte.navbar.navbar.ballSize
//import com.exyte.navbar.navbar.utils.lerp
//import com.exyte.navbar.navbar.paths.IndentRectShape
//import com.exyte.navbar.navbar.toPx
//import com.exyte.navbar.navbar.toPxf
//
//class ChangeIndentHeightAnimation(
//    cornerRadius: Dp,
//    layoutOffset: Offset,
//    val animationSpec: AnimationSpec<Float>
//) : IndentShapeAnimation(
//    cornerRadius,
//    layoutOffset
//) {
//
//    @Composable
//    override fun animateIndentShape(toOffset: Offset): State<Shape> {
//        var from by remember { mutableStateOf(Offset.Zero) }
//        var to by remember { mutableStateOf(Offset.Zero) }
//        val fraction = remember { Animatable(0f) }
//
//        val density = LocalDensity.current
//
//        val yIndentStart = 20.dp.toPxf()
//        var yIndent by remember { mutableStateOf(yIndentStart) }
//
//        var shape = remember {
//            IndentRectShape(
//                cornerRadius.toPxf(density),
//                xIndent = toOffset.x + layoutOffset.x,
//                yIndent = yIndent,
//                ballSize = ballSize.toPxf(density) / 2f
//            )
//        }
//
//        LaunchedEffect(toOffset) {
//            if (to != toOffset) {
//                from = to
//                to = toOffset
//
//                fraction.snapTo(0f)
//                fraction.animateTo(2f, tween(1000))
//            }
//        }
//
//        val ballSizePx = ballSize.toPx()
//
//        return remember {
//            derivedStateOf {
//                if (fraction.value <= 1f) {
//                    yIndent = lerp(yIndentStart, 0f, fraction.value)
//                    shape = IndentRectShape(
//                        shape.cornerRadius,
//                        from.x + layoutOffset.x + ballSizePx / 2,
//                        yIndent,
//                        shape.ballSize
//                    )
//                    shape
//                } else {
//                    yIndent = lerp(0f, yIndentStart, fraction.value-1f)
//                    shape = IndentRectShape(
//                        shape.cornerRadius,
//                        to.x + layoutOffset.x + ballSizePx / 2,
//                        yIndent,
//                        shape.ballSize
//                    )
//                    shape
//                }
//            }
//        }
//    }
//}