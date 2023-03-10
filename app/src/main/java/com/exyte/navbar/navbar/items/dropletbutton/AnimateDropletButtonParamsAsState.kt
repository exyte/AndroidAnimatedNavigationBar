package com.exyte.navbar.navbar.items.dropletbutton

import androidx.annotation.FloatRange
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.exyte.navbar.navbar.utils.lerp
import java.lang.Float.max
import java.lang.Float.min

@Stable
data class DropletButtonParams(
    @FloatRange(from = 0.0, to = 1.0) val scale: Float,
    val radius: Float,
    val verticalOffset: Float,
)

@Composable
fun animateDropletButtonAsState(
    isSelected: Boolean,
    animationSpec: AnimationSpec<Float> = remember { tween(300) }
): State<DropletButtonParams> {
    var from by remember { mutableStateOf(isSelected) }
    var to by remember { mutableStateOf(isSelected) }

    val animSpec by rememberUpdatedState(animationSpec)

    val fraction1 = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = animSpec
    )

    var dropletButtonParams by remember {
        mutableStateOf(DropletButtonParams(scale = 1f, radius = 10f, verticalOffset = 0f))
    }

    LaunchedEffect(isSelected) {
        if (to != isSelected) {
            from = to
            to = isSelected
        }
    }

    return remember {
        derivedStateOf {
            dropletButtonParams = dropletButtonParams.copy(
                scale = if (to) scaleInterpolation(fraction1.value) else 1f,
                radius = if (to) lerp(0f, 40f, fraction1.value) else 0f,
                verticalOffset = lerp(0f, 35f, fraction1.value)
            )
            dropletButtonParams
        }
//        dropletButtonParams = dropletButtonParams.copy(
//            scale = if (to) scaleInterpolation(fraction1.value) else 1f,
//            radius = if (to) radiusOffsetInterpolation(fraction1.value) * 40f else 0f,
//            verticalOffset = verticalOffsetInterpolation(fraction1.value)
//        )
//        dropletButtonParams
    }
}

fun verticalOffsetInterpolation(fraction: Float) = min(fraction * 5f, 1f) * 15

fun radiusOffsetInterpolation(fraction: Float) = if (fraction < 0.3f) {
    0f
} else {
    (fraction - 0.3f) * 1f / 0.7f
}

fun scaleInterpolation(fraction: Float): Float {
    val fraction = if (fraction < 0.3f) {
        fraction * 3.33f
    } else {
        max((0.6f - fraction) * 3.33f, 0f)
    }
    return 1f - 0.2f * fraction
}