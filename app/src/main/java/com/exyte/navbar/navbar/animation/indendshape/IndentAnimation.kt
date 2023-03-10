package com.exyte.navbar.navbar.animation.indendshape

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

abstract class IndentAnimation(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(500),
    open val indentWidth: Dp = 50.dp,
    open val indentHeight: Dp = 20.dp,
) {

    @Composable
    abstract fun animateIndentShapeAsState(targetOffset: Offset, cornerRadius: Float): State<Shape>
}