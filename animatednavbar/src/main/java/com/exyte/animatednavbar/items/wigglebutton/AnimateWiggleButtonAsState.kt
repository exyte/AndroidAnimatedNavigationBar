package com.exyte.animatednavbar.items.wigglebutton

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Stable
data class WiggleButtonParams(
    @FloatRange(from = 0.0, to = 1.0) val scale: Float,
    @FloatRange(from = 0.0, to = 1.0) val alpha: Float,
    val radius: Float
)

@Composable
fun animateWiggleButtonAsState(
    isSelected: Boolean,
    enterExitAnimationSpec: AnimationSpec<Float>,
    wiggleAnimationSpec: AnimationSpec<Float>,
    size: Float,
): State<WiggleButtonParams> {
    var from by remember { mutableStateOf(isSelected) }
    var to by remember { mutableStateOf(isSelected) }
    val fraction = remember { Animatable(0f) }

    var wiggleButtonParams by remember {
        mutableStateOf(WiggleButtonParams(scale = 1f, alpha = 1f, radius = 10f))
    }

    val fraction1 = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = enterExitAnimationSpec
    )

    val fraction2 = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = wiggleAnimationSpec
    )

    LaunchedEffect(isSelected) {
        if (to != isSelected) {
            from = to
            to = isSelected

            fraction.snapTo(0f)
            fraction.animateTo(1f)
        }
    }


    return remember {
        derivedStateOf {
            wiggleButtonParams = wiggleButtonParams.copy(
                scale = scaleInterpolator(fraction1.value),
                alpha = alphaInterpolator(fraction1.value),
                radius = if (to) calculateRadius(
                    maxRadius = size,
                    fraction = radiusInterpolator(fraction2.value),
                    minRadiusFraction = 0.55f
                ) else size * 0.55f
            )
            wiggleButtonParams
        }
    }
}

fun scaleInterpolator(fraction: Float): Float = 1 + fraction * 0.2f

fun alphaInterpolator(fraction: Float): Float = fraction / 2 + 0.5f - 0.01f

fun calculateRadius(
    maxRadius: Float,
    fraction: Float,
    @FloatRange(from = 0.0, to = 1.0) minRadiusFraction: Float = 0.1f,
) = (fraction * (1 - minRadiusFraction) + minRadiusFraction) * maxRadius

fun radiusInterpolator(
    fraction: Float
): Float = if (fraction < 0.5f) {
    fraction * 2
} else {
    (1 - fraction) * 2
}