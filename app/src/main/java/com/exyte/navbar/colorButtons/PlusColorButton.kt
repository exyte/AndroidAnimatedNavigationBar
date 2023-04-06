package com.exyte.navbar.colorButtons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
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
        Box(
            modifier = modifier
        ) {
            val color = animateColorAsState(
                targetValue = if (isSelected) Color.Black else Color.LightGray,
                label = "colorAnimation"
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.rounded_rect),
                contentDescription = null,
                tint = color.value
            )

            val layoutDirection = LocalLayoutDirection.current
            val requiredDegrees = remember(isFromLeft) {
                val rotateDegrees = if (isFromLeft) ROTATE_DEGREES else -ROTATE_DEGREES
                if (layoutDirection == LayoutDirection.Rtl) -rotateDegrees else rotateDegrees
            }


            val degrees = animateFloatAsState(
                targetValue = if (isSelected) requiredDegrees else 0f,
                animationSpec = animationSpec,
                label = "degreesAnimation"
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(if (isSelected) degrees.value else 0f),
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                tint = color.value
            )
        }
    }
}

const val ROTATE_DEGREES = 90f