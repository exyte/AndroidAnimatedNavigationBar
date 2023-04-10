package com.exyte.animatednavbar.items.dropletbutton

import androidx.annotation.FloatRange
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.exyte.animatednavbar.utils.lerp
import java.lang.Float.max

@Stable
data class DropletButtonParams(
    @FloatRange(from = 0.0, to = 1.0) val scale: Float = 1f,
    val radius: Float = 10f,
    val verticalOffset: Float = 0f,
)

@Composable
internal fun animateDropletButtonAsState(
    isSelected: Boolean,
    animationSpec: AnimationSpec<Float> = remember { tween(300) },
    size: Float,
): State<DropletButtonParams> {
    val fraction = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = animationSpec
    )
    val isAnimationRequired by rememberUpdatedState(newValue = isSelected)

    return produceState(
        initialValue = DropletButtonParams(),
        key1 = fraction.value
    ) {
        this.value = this.value.copy(
            scale = if (isAnimationRequired) scaleInterpolation(fraction.value) else 1f,
            radius = if (isAnimationRequired) lerp(0f, size, fraction.value) else 0f,
            verticalOffset = lerp(0f, size, fraction.value)
        )
    }
}

fun scaleInterpolation(fraction: Float): Float {
    val f = if (fraction < 0.3f) {
        fraction * 3.33f
    } else {
        max((0.6f - fraction) * 3.33f, 0f)
    }
    return 1f - 0.2f * f
}