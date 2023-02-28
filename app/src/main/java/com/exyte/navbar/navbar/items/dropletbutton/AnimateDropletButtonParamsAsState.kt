package com.exyte.navbar.navbar.items.dropletbutton

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.exyte.navbar.navbar.lerp
import java.lang.Float.max
import java.lang.Float.min

@Stable
data class DropletButtonParams(
    @FloatRange(from = 0.0, to = 1.0) val scale: Float,
    val radius: Float,
    val verticalOffset: Float,
)

@Composable
fun animateDropletButtonAsState(isSelected: Boolean): State<DropletButtonParams> {
    var from by remember { mutableStateOf(isSelected) }
    var to by remember { mutableStateOf(isSelected) }

    val fraction1 = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(1000)
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
                verticalOffset = verticalOffsetInterpolation(fraction1.value)
//                lerp(0f, 20f, fraction1.value)
            )
            dropletButtonParams
        }
    }
}

fun verticalOffsetInterpolation(fraction: Float) = min(fraction * 5f, 1f) * 15


fun scaleInterpolation(fraction: Float): Float {
    val fraction = if (fraction < 0.3f) { // 0...0.3 -> 0...1
        fraction * 3.33f
    } else { // 0.3...0.6 -> 1...0; 0.6...1 -> 0
        max((0.6f - fraction) * 3.33f, 0f)
    }
    return 1f - 0.2f * fraction
}