package com.exyte.navbar.navbar.items.colorButtons

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.utils.toPxf

data class CalendarAnimation(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val background: ButtonBackground,
    private val iconColor: Color = Color.Black
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
                    .align(Alignment.Center)
                    .graphicsLayer(
                        translationX = if (isSelected) offset(
                            10f,
                            fraction.value,
                            isFromLeft
                        ) else 0f
                    ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconColor
            )

            CalendarPoint(
                modifier = Modifier.align(Alignment.Center),
                offsetX = if (isSelected) offset(15f, fraction.value, isFromLeft) else 0f,
                iconColor = iconColor
            )
        }
    }

    fun offset(maxHorizontalOffset: Float, fraction: Float, isFromLeft: Boolean): Float {
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
    Box(
        modifier = modifier
            .graphicsLayer(translationX = 3.5.dp.toPxf() + offsetX, translationY = 5.dp.toPxf())
            .size(3.dp)
            .clip(CircleShape)
            .background(iconColor)
    )
}