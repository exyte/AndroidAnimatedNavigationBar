package com.exyte.navbar.navbar.items.colorButtons

import android.util.Log
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.utils.lerp
import com.exyte.navbar.navbar.utils.noRippleClickable
import com.exyte.navbar.navbar.utils.toDp
import kotlin.math.PI
import kotlin.math.sin

class BellAnimation(
    override val animationSpec: FiniteAnimationSpec<Float>,
    override val background: ButtonBackground,
) : AnimationType(animationSpec, background) {

    @Composable
    override fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        isToLeft: Boolean,
        index: Int,
        icon: Int,
        background: ButtonBackground,
        onClick: () -> Unit,
        state: State<Destination>,
    ) {

        val fraction = animateFloatAsState(
            targetValue = if (isSelected) {
                1f
            } else {
                0f
            },
            animationSpec = animationSpec
        )

        Box(
            modifier = modifier
                .noRippleClickable {
                    onClick()
                }
        ) {

            fun rightTranslation(fraction: Float) = lerp(-20f, 0f, fraction)
            fun leftTranslation(fraction: Float) = lerp(0f, 20f, fraction)

            val offset by remember {
                derivedStateOf {
//
//                    if (fraction.value == 0f) {
//                        if(isFromLeft) (fraction.value - 1) * 20 else (fraction.value - 1) * -15
//                    } else {
//
//                    }
                    Log.e(
                        "vmnjrekf",
                        "isFromLeft ${isFromLeft} isSelected ${isSelected} index ${index}"
                    )

                    when (state.value) {
                        Destination.FromToLeft -> {
                            lerp(20f, 0f, fraction.value)
                        }
                        Destination.FromToRight -> {
                            lerp(0f, 20f, fraction.value)
                        }
                        Destination.ToFromRight -> {
                            lerp(20f, 0f, fraction.value)
                        }
                        Destination.ToFromLeft -> {
                            lerp(-20f, 0f, fraction.value)
                        }
                        Destination.Nothing -> {0f}
                    }
//                    when {
//                        isFromLeft && isSelected -> {
//                            rightTranslation(fraction.value)
//                        }
//                        isFromLeft && !isSelected -> {
//                            leftTranslation(fraction.value)
//                        }
//                        !isFromLeft && isSelected -> {
//                            lerp(20f,0f, fraction.value)
////                            leftTranslation(fraction.value)
//                        }
//                        !isToLeft && !isSelected -> {
//                            rightTranslation(fraction.value)
//                        }
//                        else -> {
//                            0f
//                        }
//                    }
//                    if (isSelected) {
//                        if (isFromLeft) (fraction.value - 1) * 20 else (fraction.value - 1) * -15
//                    } else {
//                        if (isToLeft) (fraction.value - 1) * 20 else (fraction.value - 1) * -15
//                    }


//                    if (isFromLeft) (fraction.value - 1) * 20 else (fraction.value - 1) * -20
                }
            }

            Log.e("offset", "index: $index ${offset}")

            Box(
                modifier = Modifier
                    .offset(x = offset.toDp())
//                    .graphicsLayer(
//                        translationX = offset
//                    )
                    .align(Alignment.Center)
            ) {
                when {
                    background.shape != null -> {
                        Box(
                            modifier = Modifier
                                .clip(shape = background.shape.shape)
                                .size(20.dp, 20.dp)
                                .background(background.shape.color)
                                .align(Alignment.Center)
                        )
                    }
                    background.icon != null -> {
                        Image(
                            modifier = Modifier
                                .offset(x = background.offset.x, y = background.offset.y)
                                .align(Alignment.Center),
                            painter = painterResource(id = background.icon),
                            contentDescription = null
                        )
                    }
                }
            }

            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0.1f,
                        ),
                        rotationZ = degreesRotationInterpolation(fraction.value)
                    ),
                painter = painterResource(id = icon),
                contentDescription = null
            )

        }
    }

    private fun degreesRotationInterpolation(fraction: Float) =
        sin(fraction * 2 * PI).toFloat() * 20f
}