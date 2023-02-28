package com.exyte.navbar.navbar.animation.shape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

interface IndentShapeAnimation {

    @Composable
    fun animateIndentShape(toOffset: Offset): State<Shape>
}