package com.exyte.navbar.navbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.layout.animatedNavBarMeasurePolicy
import com.exyte.navbar.navbar.utils.toPx


val ballSize = 10.dp

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    @DrawableRes selectedIndex: Int,
    barColor: Color = Color.White,
    ballColor: Color = Color.Red,
    cornerRadius: Dp = 0.dp,
    ballAnimation: BallAnimation,
    indentAnimation: IndentAnimation,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current

    val ballPosition = remember { mutableStateOf(Offset.Zero) }

    var shapeInfo by remember {
            mutableStateOf(ShapeInfo(cornerRadius = cornerRadius, layoutOffset = Offset.Zero))
    }

    val indentShape =
        indentAnimation.animateIndentShapeAsState(
            shapeInfo = shapeInfo,
            toOffset = ballPosition.value
        )

    val ballAnimInfoState = ballAnimation.animateAsState(toOffset = ballPosition.value)

    val measurePolicy = animatedNavBarMeasurePolicy(
        selectedIndex = selectedIndex,
        ballPosition = ballPosition,
    )

    Layout(
        modifier = modifier
            .onGloballyPositioned {
                shapeInfo = shapeInfo.copy(
                    layoutOffset = it.positionInParent()
                )
            }
            .graphicsLayer {
                clip = true
                shape = indentShape.value
            }
            .background(barColor),
        content = content,
        measurePolicy = measurePolicy
    )

    ColorBall(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = (ballAnimInfoState.value.offset.x + shapeInfo.layoutOffset.x - ballSize.toPx(
                        density
                    ) / 2).toInt(),
                    y = (ballAnimInfoState.value.offset.y + shapeInfo.layoutOffset.y).toInt()
                )
            }
            .graphicsLayer(
                scaleY = ballAnimInfoState.value.scale,
                scaleX = ballAnimInfoState.value.scale,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, 0f)
            ),
        ballColor = ballColor,
        sizeDp = 10.dp
    )
}

@Composable
fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    sizeDp: Dp
) {
    Box(
        modifier = modifier
            .size(sizeDp)
            .clip(shape = CircleShape)
            .background(ballColor)
    )
}