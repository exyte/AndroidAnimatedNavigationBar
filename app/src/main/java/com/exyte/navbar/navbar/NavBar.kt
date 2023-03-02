package com.exyte.navbar.navbar

import android.graphics.Path
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.ui.theme.LightGray


val ballSize = 10.dp
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
    val density = LocalDensity.current

    val ballPosition = remember {
        mutableStateOf(IntOffset.Zero)
    }

    val shapeInfo by remember {
        derivedStateOf {
            mutableStateOf(ShapeInfo(cornerRadius = cornerRadius, layoutOffset = Offset.Zero))
        }
    }

    val indentShape =
        indentAnimation.animateIndentShapeAsState(
            shapeInfo = shapeInfo.value,
            toOffset = ballPosition.value.toOffset()
        )


    val ballAnimInfoState = ballAnimation.animateAsState(toOffset = ballPosition.value.toOffset())


    val measurePolicy = animatedNavBarMeasurePolicy(
        selectedIndex = selectedIndex,
        ballPosition = ballPosition,
    )

    Layout(
        modifier = modifier
            .onGloballyPositioned {
                shapeInfo.value = shapeInfo.value.copy(
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


    Log.e("cm;ed", "${ballAnimInfoState.value.scale}")

    ColorPoint(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = (ballAnimInfoState.value.offset.x + shapeInfo.value.layoutOffset.x - ballSize.toPx(
                        density
                    ) / 2).toInt(),
                    y = (ballAnimInfoState.value.offset.y + shapeInfo.value.layoutOffset.y).toInt()
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
fun animateScale(toOffset: Offset): State<Float> {
    var from by remember { mutableStateOf(Offset.Zero) }
    var to by remember { mutableStateOf(Offset.Zero) }
    val fraction = remember { Animatable(0f) }

    val density = LocalDensity.current

    LaunchedEffect(toOffset) {
        if (to != toOffset) {
            from = to
            to = toOffset

            fraction.snapTo(0f)
            fraction.animateTo(2f)
        }
    }

    val a = animateFloatAsState(
        targetValue = 1f,
        animationSpec = keyframes {
            durationMillis = 10000
            0.9f at 0
            0f at 5000
            0f at 7000
            1f at 10000
        }
    )

//            val offset = animateOffsetAsState(
//                targetValue = toOffset,
//                animationSpec = animationSpec
//            )
    return a
}

@Composable
fun animateBallParabolicAsState(toOffset: IntOffset): State<Rect> {
    var from by remember { mutableStateOf(IntOffset.Zero) }
    var to by remember { mutableStateOf(IntOffset.Zero) }
    val fraction = remember { Animatable(0f) }

    val path = remember { Path() }
    val pathMeasurer = remember { android.graphics.PathMeasure() }
    val pathLength = remember { mutableStateOf(0f) }
    val pos = remember { floatArrayOf(0f, 0f) }
    val tan = remember { floatArrayOf(0f, 0f) }

    var point by remember { mutableStateOf(IntOffset.Zero) }

    var rect by remember { mutableStateOf(Rect.Zero) }

    LaunchedEffect(toOffset) {
        if (to != toOffset) {
            from = to
            to = toOffset

            path.reset()
            path.moveTo(from.x.toFloat(), from.y.toFloat())
            path.quadTo(
                (from.x + to.x) / 2f,
                from.y - 300f,
                to.x.toFloat(),
                to.y.toFloat()
            )

            pathMeasurer.setPath(path, false)
            pathLength.value = pathMeasurer.length

            fraction.snapTo(0f)
            fraction.animateTo(1f, tween(1000))
        }
    }

    val ballSizePx = ballSize.toPx()

    return remember {
        derivedStateOf {
            pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
            point = point.copy(x = pos[0].toInt(), y = pos[1].toInt())
            rect = rect.copy(
                point.x.toFloat(),
                point.y.toFloat(),
                point.x.toFloat() + ballSizePx,
                bottom = point.x.toFloat() + ballSizePx
            )
            rect
        }
    }
}

@Composable
fun animateBallStraightAsState(toOffset: IntOffset): State<Rect> {
    val density = LocalDensity.current
    val ballRect = remember {
        mutableStateOf(
            Rect(
                offset = Offset.Zero,
                size = Size(width = ballSize.toPxf(density), height = ballSize.toPxf(density))
            )
        )
    }
    val offset = animateIntOffsetAsState(
        targetValue = toOffset,
        animationSpec = tween(1000)
    )
    return remember {
        derivedStateOf {
            ballRect.value = ballRect.value.copy(
                left = offset.value.x.toFloat(),
                top = offset.value.y.toFloat(),
                right = offset.value.x.toFloat() + ballRect.value.size.width,
                bottom = offset.value.y.toFloat() + ballRect.value.size.height
            )
            ballRect.value
        }
    }
}


@Composable
fun animateBallTeleportAsState(
    toOffset: IntOffset,
): State<androidx.compose.ui.geometry.Rect> {
    var from by remember { mutableStateOf(IntOffset.Zero) }
    var to by remember { mutableStateOf(IntOffset.Zero) }
    val fraction = remember { Animatable(0f) }

    var point by remember { mutableStateOf(IntOffset.Zero) }

    var size by remember { mutableStateOf(ballSize) }

    var offset by remember { mutableStateOf(IntOffset.Zero) }

    LaunchedEffect(toOffset) {
        if (to != toOffset) {
            from = to
            to = toOffset

            Log.e("vnmekr", "${size.value}")

            fraction.snapTo(0f)
            fraction.animateTo(1f, tween(1000))
        }
    }

    val density = LocalDensity.current
    val ballSizePx = ballSize.toPx()

    return remember {
        derivedStateOf {
            size = lerp(0.dp, ballSize, fraction.value)
            offset = offset.copy(
                x = lerp(
                    to.x.toFloat(),
                    to.x.toFloat() - ballSizePx / 2,
                    fraction.value
                ).toInt()
            )
            Rect(
                offset = offset.toOffset(),
                size = Size(width = size.toPxf(density), height = size.toPxf(density))
            )
        }
    }
}

@Composable
fun animatedNavBarMeasurePolicy(
    selectedIndex: Int,
    ballPosition: MutableState<IntOffset>,
) = remember(
    selectedIndex,
    ballPosition,
) {
    measureMeasurePolicy(
        selectedIndex = selectedIndex,
        ballPosition = ballPosition,
    )
}

internal fun measureMeasurePolicy(
    selectedIndex: Int,
    ballPosition: MutableState<IntOffset>,
) = MeasurePolicy { measurables, constraints ->

    val width = constraints.maxWidth/measurables.size

    val placeables = measurables.map { measurable ->
        measurable.measure(constraints.copy(maxWidth = width))
    }

    val gap = calculateGap(placeables, constraints.maxWidth)

    val height = placeables.maxOf { it.height }

    layout(constraints.maxWidth, height) {
        var xPosition = gap

        placeables.forEachIndexed { index, placeable ->
            placeables[index].placeRelative(xPosition, 0)

            if (selectedIndex == index) {
                ballPosition.value =
                    ballPosition.value.copy(
                        x = calculatePointPosition(
                            xPosition,
                            placeables[index].width,
                        )
                    )
            }
            xPosition += placeables[index].width + gap
        }
    }
}

@Composable
fun ColorPoint(
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

fun calculatePointPosition(xButtonPosition: Int, buttonWidth: Int): Int {
    return xButtonPosition + (buttonWidth / 2)
}

fun calculateGap(placeables: List<Placeable>, width: Int): Int {
    var allWidth = 0
    placeables.forEachIndexed { index, placeable ->
        allWidth += placeable.width
    }
    return (width - allWidth) / (placeables.size + 1)
}

@Stable
fun Int.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun Float.toDp(density: Density): Dp = with(density) { this@toDp.toDp() }

@Stable
fun TextUnit.toPx(density: Density): Int = with(density) { this@toPx.roundToPx() }

@Stable
fun Dp.toPx(density: Density): Int = with(density) { this@toPx.roundToPx() }

@Stable
fun Dp.toPxf(density: Density): Float = with(density) { this@toPxf.toPx() }

@Stable
@Composable
fun Dp.toPx(): Int = toPx(LocalDensity.current)

@Stable
@Composable
fun Dp.toPxf(): Float = toPxf(LocalDensity.current)

@Stable
@Composable
fun Float.toDp() = this.toDp(LocalDensity.current)

@Stable
@Composable
fun Int.toDp() = this.toDp(LocalDensity.current)