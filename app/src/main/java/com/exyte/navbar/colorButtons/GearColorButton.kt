package com.exyte.navbar.colorButtons

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
        val degree = animateFloatAsState(
            targetValue = if (isSelected) maxGearAnimationDegree else 0f,
            animationSpec = animationSpec
        )

        Icon(
            modifier = modifier
                .rotate(if (isSelected) degree.value else 0f),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconColor
        )
    }
}
