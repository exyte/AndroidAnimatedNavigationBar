package com.exyte.animatednavbar.items.wigglebutton

import androidx.annotation.FloatRange
import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Stable
data class WiggleButtonParams(
    @FloatRange(from = 0.0, to = 1.0) val scale: Float = 1f,
    @FloatRange(from = 0.0, to = 1.0) val alpha: Float = 1f,
    val radius: Float = 10f
)

@Composable
fun animateWiggleButtonAsState(
    isSelected: Boolean,
    enterExitAnimationSpec: AnimationSpec<Float>,
    wiggleAnimationSpec: AnimationSpec<Float>,
    maxRadius: Float,
): State<WiggleButtonParams> {
    val enterExitFraction = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = enterExitAnimationSpec
    )

    val wiggleFraction = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = wiggleAnimationSpec
    )

    val isAnimationRequired by rememberUpdatedState(newValue = isSelected)

    return produceState(
        initialValue = WiggleButtonParams(),
        key1 = enterExitFraction.value,
        key2 = wiggleFraction.value
    ) {
        this.value = this.value.copy(
            scale = scaleInterpolator(enterExitFraction.value),
            alpha = alphaInterpolator(enterExitFraction.value),
            radius = if (isAnimationRequired) calculateRadius(
                maxRadius = maxRadius * 0.8f,
                fraction = radiusInterpolator(wiggleFraction.value),
                minRadius = mildRadius * maxRadius
            ) else mildRadius * maxRadius
        )
    }
}

const val mildRadius = 0.55f

fun scaleInterpolator(fraction: Float): Float = 1 + fraction * 0.2f

fun alphaInterpolator(fraction: Float): Float = fraction / 2 + 0.5f - 0.01f

fun calculateRadius(
    maxRadius: Float,
    fraction: Float,
    minRadius: Float,
) = (fraction * (maxRadius - minRadius)) + minRadius

fun radiusInterpolator(
    fraction: Float
): Float = if (fraction < 0.5f) {
    fraction * 2
} else {
    (1 - fraction) * 2
}