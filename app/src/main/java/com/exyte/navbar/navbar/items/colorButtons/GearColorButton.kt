package com.exyte.navbar.navbar.items.colorButtons

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

class GearColorButton(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val background: ButtonBackground,
    private val iconColor: Color = Color.Black,
    private val maxGearAnimationDegree: Float = 50f,
) : ColorButtonAnimation(animationSpec, background) {

    @Composable
    override fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    ) {

        val fraction = animateFloatAsState(
            targetValue = if (isSelected) 1f else 0f,
            animationSpec = animationSpec
        )

        Icon(
            modifier = modifier
                .rotate(
                    if (isSelected) gearRotateInterpolation(
                        maxGearAnimationDegree,
                        fraction.value
                    ) else 0f
                ),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconColor
        )
    }

    private fun gearRotateInterpolation(maxDegree: Float, fraction: Float) =
        fraction * maxDegree
}
