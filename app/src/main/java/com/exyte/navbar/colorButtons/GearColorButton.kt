package com.exyte.navbar.colorButtons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import com.exyte.navbar.ui.theme.LightGrey

class GearColorButton(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val background: ButtonBackground,
    private val maxGearAnimationDegree: Float = 50f,
) : ColorButtonAnimation(animationSpec, background) {

    @Composable
    override fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    ) {
        val layoutDirection = LocalLayoutDirection.current
        val gearAnimationDegree = remember {
            if (layoutDirection == LayoutDirection.Ltr) {
                maxGearAnimationDegree
            } else {
                -maxGearAnimationDegree
            }
        }
        val degree = animateFloatAsState(
            targetValue = if (isSelected) gearAnimationDegree else 0f,
            animationSpec = animationSpec,
            label = "degreeAnimation"
        )

        val color = animateColorAsState(
            targetValue = if (isSelected) Color.Black else LightGrey,
            label = "colorAnimation"
        )

        Icon(
            modifier = modifier
                .rotate(if (isSelected) degree.value else 0f),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color.value
        )
    }
}
