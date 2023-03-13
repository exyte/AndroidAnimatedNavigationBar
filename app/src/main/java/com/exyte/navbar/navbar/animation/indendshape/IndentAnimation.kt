package com.exyte.navbar.navbar.animation.indendshape

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

/**
 * defining indent animation, that is above selected item.
 */

interface IndentAnimation {

    /**
     *@param [targetOffset] target offset
     *@param [cornerRadius] corner radius of the navBar layout
     */
    @Composable
    fun animateIndentShapeAsState(targetOffset: Offset, cornerRadius: Float): State<Shape>
}