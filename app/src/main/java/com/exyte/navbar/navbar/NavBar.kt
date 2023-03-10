package com.exyte.navbar.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import com.exyte.navbar.navbar.animation.balltrajectory.BallAnimInfo
import com.exyte.navbar.navbar.animation.balltrajectory.BallAnimation
import com.exyte.navbar.navbar.animation.indendshape.IndentAnimation
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.layout.animatedNavBarMeasurePolicy
import com.exyte.navbar.navbar.utils.ballTransform
import com.exyte.navbar.navbar.utils.toPxf

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color = Color.White,
    ballColor: Color = Color.Red,
    cornerRadius: Dp = 0.dp,
    ballAnimation: BallAnimation,
    indentAnimation: IndentAnimation,
    content: @Composable () -> Unit,
) {

    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }
    val measurePolicy = animatedNavBarMeasurePolicy {
        itemPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    val selectedItemOffset by remember(selectedIndex, itemPositions) {
        derivedStateOf {
            if (itemPositions.isNotEmpty()) itemPositions[selectedIndex] else Offset.Unspecified
        }
    }

    var shapeInfo by remember {
        mutableStateOf(ShapeInfo(cornerRadius = cornerRadius, layoutOffset = Offset.Unspecified))
    }

    val density = LocalDensity.current
    val indentShape = indentAnimation.animateIndentShapeAsState(
        cornerRadius = cornerRadius.toPxf(density),
        targetOffset = selectedItemOffset
    )

    val ballAnimInfoState = ballAnimation.animateAsState(
        toOffset = selectedItemOffset,
        layoutShapeInfo = shapeInfo
    )

    Layout(
        modifier = modifier
            .onGloballyPositioned {
                shapeInfo = shapeInfo.copy(layoutOffset = it.positionInParent())
            }
            .graphicsLayer {
                clip = true
                shape = indentShape.value
            }
            .background(barColor),
        content = content,
        measurePolicy = measurePolicy
    )

    if (ballAnimInfoState.value.offset.isSpecified) {
        ColorBall(
            ballAnimInfo = ballAnimInfoState.value,
            ballColor = ballColor,
            sizeDp = ballSize
        )
    }
}

val ballSize = 10.dp

@Composable
fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    ballAnimInfo: BallAnimInfo,
    sizeDp: Dp,
) {
    Box(
        modifier = modifier
            .ballTransform(ballAnimInfo)
            .size(sizeDp)
            .clip(shape = CircleShape)
            .background(ballColor)
    )
}