package com.exyte.navbar.colorButtons

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.exyte.navbar.R

class PlusColorButton(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val background: ButtonBackground,
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

        Box(
            modifier = modifier
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.icon_rect),
                contentDescription = null
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(if (isSelected) plusRotationInterpolation(fraction.value) else 0f),
                painter = painterResource(id = R.drawable.icon_plus),
                contentDescription = null
            )

        }

    }

    private fun plusRotationInterpolation(fraction: Float) = fraction * 90f
}