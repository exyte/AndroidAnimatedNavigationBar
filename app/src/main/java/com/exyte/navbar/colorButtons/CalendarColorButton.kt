package com.exyte.navbar.colorButtons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.utils.toPxf
import com.exyte.navbar.ui.theme.LightGrey


data class CalendarAnimation(
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

        Box(
            modifier = modifier
        ) {
            val fraction = animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = animationSpec,
                label = "fractionAnimation"
            )

            val layoutDirection = LocalLayoutDirection.current
            val isLeftAnimation = remember(isFromLeft) {
                if (layoutDirection == LayoutDirection.Ltr) {
                    isFromLeft
                } else {
                    !isFromLeft
                }
            }

            val color = animateColorAsState(
                targetValue = if (isSelected) Color.Black else LightGrey,
                label = "colorAnimation"
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(
                        translationX = if (isSelected) offset(
                            10f,
                            fraction.value,
                            isLeftAnimation
                        ) else 0f
                    ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color.value
            )

            CalendarPoint(
                modifier = Modifier.align(Alignment.Center),
                offsetX = if (isSelected) offset(
                    15f,
                    fraction.value,
                    isLeftAnimation
                ) else 0f,
                iconColor = color.value
            )
        }
    }

    private fun offset(maxHorizontalOffset: Float, fraction: Float, isFromLeft: Boolean): Float {
        val maxOffset = if (isFromLeft) -maxHorizontalOffset else maxHorizontalOffset
        return if (fraction < 0.5) {
            2 * fraction * maxOffset
        } else {
            2 * (1 - fraction) * maxOffset
        }
    }
}

@Composable
fun CalendarPoint(
    modifier: Modifier = Modifier,
    offsetX: Float,
    iconColor: Color,
) {
    val layoutDirection = LocalLayoutDirection.current
    val density = LocalDensity.current
    val internalOffset = remember {
        if (layoutDirection == LayoutDirection.Ltr) {
            Offset(3.5.dp.toPxf(density), 5.dp.toPxf(density))
        } else {
            Offset((-3.5).dp.toPxf(density), 5.dp.toPxf(density))
        }
    }
    Box(
        modifier = modifier
            .graphicsLayer(
                translationX = internalOffset.x + offsetX,
                translationY = internalOffset.y
            )
            .size(3.dp)
            .clip(CircleShape)
            .background(iconColor)
    )
}