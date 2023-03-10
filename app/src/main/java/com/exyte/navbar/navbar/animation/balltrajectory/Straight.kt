package com.exyte.navbar.navbar.animation.balltrajectory

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.platform.LocalDensity
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.ballSize
import com.exyte.navbar.navbar.utils.toPxf

class Straight(
    val animationSpec: AnimationSpec<Offset>
) : BallAnimation {
    @Composable
    override fun animateAsState(toOffset: Offset, layoutShapeInfo: ShapeInfo): State<BallAnimInfo> {
        if (toOffset.isUnspecified && layoutShapeInfo.layoutOffset.isUnspecified) {
            return derivedStateOf { BallAnimInfo() }
        }

        var ballAnimInfo by remember { mutableStateOf(BallAnimInfo()) }
        val offset = animateOffsetAsState(
            targetValue = calculateOffset(
                toOffset,
                layoutShapeInfo,
                ballSize.toPxf(LocalDensity.current)
            ),
            animationSpec = animationSpec
        )

        return remember {
            derivedStateOf {
                ballAnimInfo = ballAnimInfo.copy(offset = offset.value)
                ballAnimInfo
            }
        }
    }

    private fun calculateOffset(offset: Offset, layoutShapeInfo: ShapeInfo, ballSizePx: Float) =
        Offset(
            x = offset.x + layoutShapeInfo.layoutOffset.x - ballSizePx / 2f,
            y = offset.y + layoutShapeInfo.layoutOffset.y
        )
}