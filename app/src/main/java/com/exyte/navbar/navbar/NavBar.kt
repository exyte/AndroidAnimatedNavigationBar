package com.exyte.navbar.navbar

import android.graphics.Path
import android.graphics.Point
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.paths.IndentRectShape

@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color = Color.White,
    selectedColor: Color = Color.Red,
    unselectedColor: Color = Color.Black,
    ballColor: Color = Color.Red,
    cornerRadius: Dp = 0.dp,
    verticalPadding: Dp = 10.dp,
    ballAnimation: Any = Any(),
    indentAnimation: Any = Any(),
    buttonsAnimation: Any = Any(),
    ballTrajectory: BallTrajectory = BallTrajectory.parabolic,
    content: @Composable () -> Unit,
) {

    val isTrue = remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .clickable {
                isTrue.value = !isTrue.value
            }
    ) {

        val a = animateFloatAsState(
            targetValue = if (isTrue.value) 100f else 0f,
            animationSpec = tween(1000)
        )

        CustomNavBarLayout(content, selectedIndex)
    }
}


val ballSize = 10.dp

@Composable
fun CustomNavBarLayout(
    content: @Composable () -> Unit,
    selectedIndex: Int,
) {

    var ballPosition = remember {
        mutableStateOf(IntOffset.Zero)
    }

    val ballPrevPosition = remember { mutableStateOf(0) }
    val t = remember {
        Animatable(0f)
//        TargetBasedAnimation(
//            animationSpec = tween(200),
//            typeConverter = Float.VectorConverter,
//            initialValue = 0f,
//            targetValue = 1f
//        )
    }

    val pointsPositions = remember { mutableListOf<Int>(0, 0, 0, 0, 0, 0) }

//    val prevBallXPosition = rememberPrevious(current = ballPosition.value.x)

//    Log.e("vmref","${ballPosition.value.x} ${prevBallXPosition}")

    LaunchedEffect(ballPosition) {
        t.snapTo(0f)
        t.animateTo(1f, tween(1000))
    }

    val path = remember {
        android.graphics.Path()
    }

    val pathMeasurer = remember {
        derivedStateOf {
            val from = Point(0, 0)
            val to = Point(ballPosition.value.x, 0)
            path.reset()
            path.moveTo(from.x.toFloat(), from.y.toFloat())
            path.quadTo(
                (from.x + to.x) / 2f, from.y - 100f, to.x.toFloat(), to.y.toFloat()
            )
            android.graphics.PathMeasure(path, false)
        }
    }

//    val position = remember {
//        derivedStateOf {
//            val length = pathMeasurer.value.length
//            var pos = floatArrayOf(0f, 0f)
//            var tan = floatArrayOf(0f, 0f)
//            pathMeasurer.value.getPosTan(length * t.value, pos, tan)
//            PointF(pos[0], pos[1])
//        }
//    }

//    val position = animateIntOffsetAsState(
//        targetValue = ballPosition.value,
//        animationSpec = tween(1000)
//    )

    val position = animatePathAsState(toPoint = Point(ballPosition.value.x, ballPosition.value.y))

    val measurePolicy = animatedNavBarMeasurePolicy(
        selectedIndex = selectedIndex,
        ballPosition = ballPosition,
        ballXPosition = t.value,
        pointsPositions = pointsPositions,
        position = position,
    )

    Layout(
        modifier = Modifier
            .clip(IndentRectShape())
            .background(Color.Blue),
        content = {
            ColorPoint()
            content()
        },
        measurePolicy = measurePolicy
    )
}

@Composable
fun animatePathAsState(toPoint: Point): State<Point> {
    var from = remember { mutableStateOf(Point()) }
    var to = remember { mutableStateOf(Point()) }
    val fraction = remember { Animatable(0f) }

    val path = remember { Path() }
    val pathMeasurer = remember { android.graphics.PathMeasure() }
    val pathLength = remember { mutableStateOf(0f) }
    val pos = remember { floatArrayOf(0f, 0f) }
    val tan = remember { floatArrayOf(0f, 0f) }

    val point = remember { mutableStateOf(Point()) }

    LaunchedEffect(toPoint) {
        if (to != toPoint) {
            from.value = to.value
            to.value = toPoint

            path.reset()
            path.moveTo(from.value.x.toFloat(), from.value.y.toFloat())
            path.quadTo(
                (from.value.x + to.value.x) / 2f,
                from.value.y - 300f,
                to.value.x.toFloat(),
                to.value.y.toFloat()
            )

            pathMeasurer.setPath(path, false)
            pathLength.value = pathMeasurer.length

            fraction.snapTo(0f)
            fraction.animateTo(1f, tween(1000))
        }
    }

    return remember {
        derivedStateOf {
            pathMeasurer.getPosTan(pathLength.value * fraction.value, pos, tan)
            point.value.x = pos[0].toInt()
            point.value.y = pos[1].toInt()
            point.value
        }
    }
}

@Composable
fun animatedNavBarMeasurePolicy(
    selectedIndex: Int,
    ballPosition: MutableState<IntOffset>,
    ballXPosition: Float,
    pointsPositions: MutableList<Int>,
    position: State<Point>,
) = remember(
    selectedIndex,
    ballXPosition,
    ballPosition,
    pointsPositions,
) {
    measureMeasurePolicy(
        selectedIndex = selectedIndex,
        ballPosition = ballPosition,
        ballXPosition = ballXPosition,
        pointsPositions = pointsPositions,
        position = position
    )
}

internal fun measureMeasurePolicy(
    selectedIndex: Int,
    ballPosition: MutableState<IntOffset>,
    ballXPosition: Float,
    pointsPositions: MutableList<Int>,
    position: State<Point>,
) = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map { measurable ->
        measurable.measure(constraints)
    }

    val gap = calculateGap(placeables, constraints.maxWidth)

    layout(constraints.maxWidth, constraints.maxHeight) {
        var xPosition = gap
        var yPosition = 0
        var height = 0
        placeables.forEachIndexed { index, placeable ->
            if (placeables[index].height > height) {
                height = placeables[index].height
            }
        }
        yPosition = constraints.maxHeight - height

        placeables.forEachIndexed { index, placeable ->
            if (index != 0) {
                placeables[index].placeRelative(xPosition, yPosition)

                pointsPositions[index] = calculatePointPosition(
                    xPosition,
                    placeables[index].width,
                    ballSize.roundToPx()
                )

                if (selectedIndex == index - 1) {
                    ballPosition.value =
                        ballPosition.value.copy(
                            x = calculatePointPosition(
                                xPosition,
                                placeables[index].width,
                                ballSize.roundToPx()
                            )
                        )
                }
                xPosition += placeables[index].width + gap
            }
        }

        placeables[0].placeRelative(x = position.value.x, y = position.value.y + yPosition)
    }
}

@Composable
fun ColorPoint(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(10.dp)
            .clip(shape = CircleShape)
            .background(Color.Black)
    )
}

fun calculatePointPosition(xButtonPosition: Int, buttonWidth: Int, ballSize: Int): Int {
    return xButtonPosition + (buttonWidth - ballSize) / 2
}

fun calculateGap(placeables: List<Placeable>, width: Int): Int {
    var allWidth = 0
    placeables.forEachIndexed { index, placeable ->
        if (index != 0) {
            allWidth += placeable.width
        }
    }
    return (width - allWidth) / (placeables.size)
}


val selectedColor = Color(0xFFE9A75A)

val backgroundColor = Color(0xFFEEEBE7)
val accentColor = Color(0xFF625E5C)
//val backgroundColor = Color.Black

@Composable
fun AnimatedNavigationItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Int,
//    icon: @Composable () -> Unit,
    description: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    val height = animateDpAsState(
        targetValue = if (isSelected) 73.dp else 65.dp,
        animationSpec = tween(500)
    )
    val tintColor = animateColorAsState(
        targetValue = if (isSelected) androidx.compose.ui.graphics.Color.White else accentColor,
        animationSpec = tween(500)
    )
    val iconBackgroundColor = animateColorAsState(
        targetValue = if (isSelected) selectedColor else backgroundColor,
        animationSpec = tween(500)
    )
    Box(
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.BottomCenter)
                .background(color = backgroundColor)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(73.dp, height.value)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(color = backgroundColor)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(48.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(color = iconBackgroundColor.value)
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = icon),
                contentDescription = description,
                tint = tintColor.value
            )
        }
    }
}