package com.exyte.navbar.navbar.animation.balltrajectory

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import com.exyte.navbar.navbar.animation.shape.ShapeInfo

interface BallAnimation {

    @Composable
    fun animateAsState(toOffset: Offset, layoutShapeInfo: ShapeInfo): State<BallAnimInfo>
}

data class BallAnimInfo(
    val scale: Float = 1f,
    val offset: Offset = Offset.Unspecified
)