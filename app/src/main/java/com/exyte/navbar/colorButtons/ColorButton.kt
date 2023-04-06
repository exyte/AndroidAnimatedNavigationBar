package com.exyte.navbar.colorButtons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.utils.lerp
import com.exyte.animatednavbar.utils.noRippleClickable
import com.exyte.animatednavbar.utils.toDp
import com.exyte.animatednavbar.utils.toPxf

data class ButtonBackground(
    @DrawableRes val icon: Int,
    val offset: DpOffset = DpOffset.Zero
)

@Stable
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
        val isSelected = remember(selectedIndex, index) { selectedIndex == index }

        val fraction = animateFloatAsState(
            targetValue = if (isSelected) 1f else 0f,
            animationSpec = backgroundAnimationSpec,
            label = "fractionAnimation",
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

private fun calculateBackgroundOffset(
    isSelected: Boolean,
    isFromLeft: Boolean,
    fraction: Float,
    maxOffset: Float
): Float {
    val offset = if (isFromLeft) -maxOffset else maxOffset
    return if (isSelected) {
        lerp(offset, 0f, fraction)
    } else {
        lerp(-offset, 0f, fraction)
    }
}
