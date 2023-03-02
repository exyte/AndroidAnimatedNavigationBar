package com.exyte.navbar.navbar.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.exyte.navbar.navbar.ballSize

//class StraightNavBarAnimation(
//    val cornerRadius: Dp,
//    val layoutOffset: Offset,
//    val animationSpec: AnimationSpec<Float>
//): NavBarAnimator() {
//
//    @Composable
//    override fun animateBallAsState(toOffset: Offset): State<Rect> {
//        val density = LocalDensity.current
//        val ballRect = remember {
//            mutableStateOf(
//                Rect(
//                    offset = Offset.Zero,
//                    size = Size(width = ballSize.toPxf(density), height = ballSize.toPxf(density))
//                )
//            )
//        }
//        val offset = animateOffsetAsState(
//            targetValue = toOffset,
//            animationSpec = tween(1000)
//        )
//        return remember {
//            derivedStateOf {
//                ballRect.value = ballRect.value.copy(
//                    left = offset.value.x,
//                    top = offset.value.y,
//                    right = offset.value.x + ballRect.value.size.width,
//                    bottom = offset.value.y + ballRect.value.size.height
//                )
//                ballRect.value
//            }
//        }
//    }
//
//    private val movingIndentAnimation = MovingIndentAnimation(
//        cornerRadius = cornerRadius,
//        layoutOffset = layoutOffset,
//        animationSpec = animationSpec
//    )
//
//    @Composable
//    override fun animateIndentShape(toOffset: Offset): State<Shape> {
//        movingIndentAnimation.
//    }
//}