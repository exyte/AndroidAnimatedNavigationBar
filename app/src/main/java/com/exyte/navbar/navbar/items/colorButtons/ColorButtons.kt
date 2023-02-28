package com.exyte.navbar.navbar.items.colorButtons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.R
import com.exyte.navbar.navbar.noRippleClickable
import com.exyte.navbar.navbar.toPxf
import com.exyte.navbar.ui.theme.LightGray
import kotlin.math.PI
import kotlin.math.sin

data class ButtonBackground(
    val shape: ShapeBackground? = null,
    @DrawableRes val icon: Int? = null,
    val offset: DpOffset = DpOffset.Zero
)

data class ShapeBackground(
    val shape: Shape,
    val color: Color
)

data class AnimationIconData(
    val degrees: Float,
)

sealed class AnimationType(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(10000),
    open val background: ButtonBackground,
) {
    @Composable
    abstract fun AnimateIcon(
        modifier: Modifier,
        isSelected: Boolean,
        icon: Int,
        background: ButtonBackground,
        onClick: () -> Unit,
    ): State<AnimationIconData>

    data class BellAnimation(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val background: ButtonBackground,
    ) : AnimationType(animationSpec, background) {

        @Composable
        override fun AnimateIcon(
            modifier: Modifier,
            isSelected: Boolean,
            icon: Int,
            background: ButtonBackground,
            onClick: () -> Unit,
        ): State<AnimationIconData> {

            val fraction = animateFloatAsState(
                targetValue = if (isSelected) {
                    1f
                } else {
                    0f
                },
                animationSpec = animationSpec
            )

            var animationIconData by remember {
                mutableStateOf(
                    AnimationIconData(degrees = 0f)
                )
            }

            Box(
                modifier = modifier
                    .noRippleClickable {
                        onClick()
                    }
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


            return remember {
                derivedStateOf {
                    animationIconData =
                        animationIconData.copy(degrees = degreesRotationInterpolation(fraction.value))
                    animationIconData
                }
            }
        }

        private fun degreesRotationInterpolation(fraction: Float) =
            sin(fraction * 2 * PI).toFloat() * 20f
    }


    data class PlusAnimation(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val background: ButtonBackground,
    ) : AnimationType(animationSpec, background) {

        @Composable
        override fun AnimateIcon(
            modifier: Modifier,
            isSelected: Boolean,
            icon: Int,
            background: ButtonBackground,
            onClick: () -> Unit,
        ): State<AnimationIconData> {
            val fraction = animateFloatAsState(
                targetValue = if (isSelected) {
                    1f
                } else {
                    0f
                },
                animationSpec = animationSpec
            )

            var animationIconData by remember {
                mutableStateOf(
                    AnimationIconData(degrees = 0f)
                )
            }

            Box(
                modifier = modifier
                    .noRippleClickable {
                        onClick()
                    }
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

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.icon_rect),
                    contentDescription = null
                )

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .rotate(plusRotationInterpolation(fraction.value)),
                    painter = painterResource(id = R.drawable.icon_plus),
                    contentDescription = null
                )

            }

            return remember {
                derivedStateOf {
//                    animationIconData =
//                        animationIconData.copy(degrees = degreesRotationInterpolation(fraction.value))
                    animationIconData
                }
            }
        }

        private fun plusRotationInterpolation(fraction: Float) = fraction * 90f
    }

    data class CalendarAnimation(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val background: ButtonBackground,
        val iconColor: Color = Color.Black
    ) : AnimationType(animationSpec, background) {

        @Composable
        override fun AnimateIcon(
            modifier: Modifier,
            isSelected: Boolean,
            icon: Int,
            background: ButtonBackground,
            onClick: () -> Unit,
        ): State<AnimationIconData> {

            val fraction = animateFloatAsState(
                targetValue = if (isSelected) {
                    1f
                } else {
                    0f
                },
                animationSpec = animationSpec
            )

            var animationIconData by remember {
                mutableStateOf(
                    AnimationIconData(degrees = 0f)
                )
            }

            Box(
                modifier = modifier
                    .noRippleClickable {
                        onClick()
                    }
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

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer(
                            translationX = offset(10f, fraction.value)
                        ),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconColor
                )

                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = 3.5.dp.toPxf() + offset(15f, fraction.value),
                            translationY = 5.dp.toPxf()
                        )
                        .align(Alignment.Center)
                        .size(3.dp)
                        .clip(CircleShape)
                        .background(iconColor)
                )

            }


            return remember {
                derivedStateOf {
                    animationIconData
                }
            }
        }

        fun offset(maxHorizontalOffset: Float, fraction: Float): Float {
            return if (fraction < 0.5) {
                2 * fraction * maxHorizontalOffset
            } else {
                2 * (1 - fraction) * maxHorizontalOffset
            }
        }
    }


    data class GearAnimation(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val background: ButtonBackground,
        val iconColor: Color = Color.Black,
        val maxGearAnimationDegree: Float = 50f,
    ) : AnimationType(animationSpec, background) {

        @Composable
        override fun AnimateIcon(
            modifier: Modifier,
            isSelected: Boolean,
            icon: Int,
            background: ButtonBackground,
            onClick: () -> Unit,
        ): State<AnimationIconData> {

            val fraction = animateFloatAsState(
                targetValue = if (isSelected) {
                    1f
                } else {
                    0f
                },
                animationSpec = animationSpec
            )

            var animationIconData by remember {
                mutableStateOf(
                    AnimationIconData(degrees = 0f)
                )
            }

            Box(
                modifier = modifier
                    .noRippleClickable {
                        onClick()
                    }
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

                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .rotate(gearRotateInterpolation(maxGearAnimationDegree, fraction.value)),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconColor
                )


            }


            return remember {
                derivedStateOf {
                    animationIconData
                }
            }
        }

        private fun gearRotateInterpolation(maxDegree: Float, fraction: Float) =
            fraction * maxDegree
    }


}


@Composable
fun ColorButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String = "",
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = LightGray,
    background: ButtonBackground,
    animationType: AnimationType,
) {

    Box(
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    ) {

        animationType.AnimateIcon(
            modifier = modifier,
            isSelected = isSelected,
            icon = icon,
            background = background,
            onClick = onClick
        )
    }
}