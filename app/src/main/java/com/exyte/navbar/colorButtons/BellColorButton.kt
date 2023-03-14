package com.exyte.navbar.colorButtons

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.exyte.animatednavbar.utils.rotationWithTopCenterAnchor
import kotlin.math.PI
import kotlin.math.sin

class BellColorButton(
    override val animationSpec: FiniteAnimationSpec<Float> = tween(),
    override val background: ButtonBackground,
    private val maxDegrees: Float = 20f,
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
                .rotationWithTopCenterAnchor(
                    if (isSelected) degreesRotationInterpolation(maxDegrees, fraction.value) else 0f
                ),
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }

    private fun degreesRotationInterpolation(maxDegrees: Float, fraction: Float) =
        sin(fraction * 2 * PI).toFloat() * maxDegrees
}