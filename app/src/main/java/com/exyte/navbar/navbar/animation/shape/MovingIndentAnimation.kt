//package com.exyte.navbar.navbar.animation.shape
//
//import androidx.compose.animation.core.AnimationSpec
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.State
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.exyte.navbar.navbar.ballSize
//import com.exyte.navbar.navbar.paths.IndentRectShape
//import com.exyte.navbar.navbar.toPxf
//import kotlinx.coroutines.yield
//
//class MovingIndentAnimation(
//    cornerRadius: Dp,
//    layoutOffset: Offset,
//    animationSpec: AnimationSpec<Float>
//) : IndentShapeAnimation(cornerRadius = cornerRadius, layoutOffset = layoutOffset) {
//
//    @Composable
//    override fun animateIndentShape(toOffset: Offset): State<Shape> {
//        val density = LocalDensity.current
//
//        val shape = remember {
//            IndentRectShape(
//                cornerRadius.toPxf(density),
//                xIndent = toOffset.x + layoutOffset.x,
//                yIndent = 20.dp.toPxf(density),
//                ballSize = ballSize.toPxf(density) / 2f
//            )
//        }
//
//        val position = animateFloatAsState(
//            targetValue = toOffset.x,
//            animationSpec = tween()
//        )
//
//        return remember {
//            derivedStateOf {
//                shape.xIndent = position.value + layoutOffset.x
//                shape
//            }
//        }
//    }
//}