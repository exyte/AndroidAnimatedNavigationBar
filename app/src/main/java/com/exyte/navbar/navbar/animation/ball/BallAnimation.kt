package com.exyte.navbar.navbar.animation.ball


import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.TransformOrigin

//@Immutable
//sealed class BallTransition {
//    internal abstract val data: TransitionData
//}
//
//@Immutable
//internal data class TransitionData(
//    val fade: FadeAnimation? = null,
//    val offset: OffsetAnimation? = null,
//    val scale: ScaleAnimation? = null
//)
//
//@Immutable
//internal data class FadeAnimation(val alpha: Float, val animationSpec: FiniteAnimationSpec<Float>)
//
//@Immutable
//internal data class OffsetAnimation(
//    val trajectory: BallTrajectory,
//    val animationSpec: FiniteAnimationSpec<Float>
//)
//
//@Immutable
//internal data class ScaleAnimation(
//    val scale: Float,
//    val transformOrigin: TransformOrigin,
//    val animationSpec: FiniteAnimationSpec<Float>
//)
//
//
//interface BallAnimation {
//
//    @Composable
//    fun animateBallSizeAsState(): State<Size>
//
//    @Composable
//    fun animateBallOffsetAsState(): State<Offset>
//}