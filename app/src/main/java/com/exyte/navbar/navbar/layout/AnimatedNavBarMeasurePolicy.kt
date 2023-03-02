package com.exyte.navbar.navbar.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable

@Composable
fun animatedNavBarMeasurePolicy(
    selectedIndex: Int,
    ballPosition: MutableState<Offset>,
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
    ballPosition: MutableState<Offset>,
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

fun calculatePointPosition(xButtonPosition: Int, buttonWidth: Int): Float {
    return xButtonPosition + (buttonWidth / 2f)
}

fun calculateGap(placeables: List<Placeable>, width: Int): Int {
    var allWidth = 0
    placeables.forEachIndexed { index, placeable ->
        allWidth += placeable.width
    }
    return (width - allWidth) / (placeables.size + 1)
}