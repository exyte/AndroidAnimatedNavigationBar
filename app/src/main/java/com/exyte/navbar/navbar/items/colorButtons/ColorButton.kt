package com.exyte.navbar.navbar.items.colorButtons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.utils.lerp
import com.exyte.navbar.navbar.utils.noRippleClickable
import com.exyte.navbar.navbar.utils.toDp
import com.exyte.navbar.navbar.utils.toPxf

data class ButtonBackground(
    @DrawableRes val icon: Int,
    val offset: DpOffset = DpOffset.Zero
)

abstract class ColorButtonAnimation(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(10000),
    open val background: ButtonBackground,
) {
    @Composable
    abstract fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    )
}

@Composable
fun ColorButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    index: Int,
    selectedIndex: Int,
    prevSelectedIndex: Int,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    contentDescription: String? = null,
    background: ButtonBackground,
    backgroundAnimationSpec: AnimationSpec<Float> = remember { tween(300, easing = LinearEasing) },
    animationType: ColorButtonAnimation,
    maxBackgroundOffset: Dp = 25.dp
) {

    Box(
        modifier = modifier.noRippleClickable { onClick() }
    ) {
        val fraction = animateFloatAsState(
            targetValue = if (isSelected) 1f else 0f,
            animationSpec = backgroundAnimationSpec
        )

        val density = LocalDensity.current
        val maxOffset = remember(maxBackgroundOffset) { maxBackgroundOffset.toPxf(density) }

        val isFromLeft = remember(prevSelectedIndex, index, selectedIndex) {
            (prevSelectedIndex < index) || (selectedIndex > index)
        }
        val offset by remember(isSelected, isFromLeft) {
            derivedStateOf {
                calculateBackgroundOffset(
                    isSelected = isSelected,
                    isFromLeft = isFromLeft,
                    maxOffset = maxOffset,
                    fraction = fraction.value
                )
            }
        }

        Image(
            modifier = Modifier
                .offset(x = background.offset.x + offset.toDp(), y = background.offset.y)
                .scale(fraction.value)
                .align(Alignment.Center),
            painter = painterResource(id = background.icon),
            contentDescription = contentDescription
        )

        animationType.AnimatingIcon(
            modifier = Modifier.align(Alignment.Center),
            isSelected = isSelected,
            isFromLeft = isFromLeft,
            icon = icon,
        )
    }
}

internal fun calculateBackgroundOffset(
    isSelected: Boolean,
    isFromLeft: Boolean,
    fraction: Float,
    maxOffset: Float
) = if (isSelected) {
    if (isFromLeft) {
        lerp(-maxOffset, 0f, fraction)
    } else {
        lerp(maxOffset, 0f, fraction)
    }
} else {
    if (isFromLeft) {
        lerp(maxOffset, 0f, fraction)
    } else {
        lerp(-maxOffset, 0f, fraction)
    }
}
