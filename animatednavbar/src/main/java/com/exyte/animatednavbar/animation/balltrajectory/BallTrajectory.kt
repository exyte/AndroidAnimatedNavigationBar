package com.exyte.animatednavbar.animation.balltrajectory

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset

/**
 * Interface defining the ball animation
 */
interface BallAnimation {

    /**
     *@param [targetOffset] target offset
     */
    @Composable
    fun animateAsState(targetOffset: Offset): State<BallAnimInfo>
}

/**
 * Describes parameters of the ball animation
 */
data class BallAnimInfo(
    val scale: Float = 1f,
    val offset: Offset = Offset.Unspecified
)