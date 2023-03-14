package com.exyte.animatednavbar.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable

@Composable
fun animatedNavBarMeasurePolicy(
    onBallPositionsCalculated: (ArrayList<Float>) -> Unit
) = remember {
    barMeasurePolicy(onBallPositionsCalculated = onBallPositionsCalculated)
}

internal fun barMeasurePolicy(onBallPositionsCalculated: (ArrayList<Float>) -> Unit) =
    MeasurePolicy { measurables, constraints ->

        check(measurables.isNotEmpty()){
            "There must be at least one element"
        }
        val itemWidth = constraints.maxWidth / measurables.size
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(maxWidth = itemWidth))
        }

        val gap = calculateGap(placeables, constraints.maxWidth)
        val height = placeables.maxOf { it.height }

        layout(constraints.maxWidth, height) {
            var xPosition = gap

            val positions = arrayListOf<Float>()

            placeables.forEachIndexed { index, _ ->
                placeables[index].placeRelative(xPosition, 0)

                positions.add(
                    element = calculatePointPosition(
                        xPosition,
                        placeables[index].width,
                    )
                )

                xPosition += placeables[index].width + gap
            }
            onBallPositionsCalculated(positions)
        }
    }

fun calculatePointPosition(xButtonPosition: Int, buttonWidth: Int): Float {
    return xButtonPosition + (buttonWidth / 2f)
}

fun calculateGap(placeables: List<Placeable>, width: Int): Int {
    var allWidth = 0
    placeables.forEach { placeable ->
        allWidth += placeable.width
    }
    return (width - allWidth) / (placeables.size + 1)
}