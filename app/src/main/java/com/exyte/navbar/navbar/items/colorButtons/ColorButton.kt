package com.exyte.navbar.navbar.items.colorButtons

import android.util.Log
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
import com.exyte.navbar.navbar.utils.noRippleClickable
import com.exyte.navbar.navbar.utils.toPxf
import com.exyte.navbar.ui.theme.LightGray

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

abstract class AnimationType(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(10000),
    open val background: ButtonBackground,
) {
    @Composable
    abstract fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        isToLeft: Boolean,
        index: Int,
        icon: Int,
        background: ButtonBackground,
        onClick: () -> Unit,
        state: State<Destination>,
    )

    data class PlusAnimation(
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

        }

        private fun plusRotationInterpolation(fraction: Float) = fraction * 90f
    }

    data class CalendarAnimation(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val background: ButtonBackground,
        val iconColor: Color = Color.Black
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
        }

        private fun gearRotateInterpolation(maxDegree: Float, fraction: Float) =
            fraction * maxDegree
    }


}


enum class Destination {
    FromToRight,
    FromToLeft,
    ToFromRight,
    ToFromLeft,
    Nothing,
}

@Composable
fun ColorButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    index: Int,
    prevSelectedIndex: Int,
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String = "",
    label: @Composable() (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = LightGray,
    background: ButtonBackground,
    animationType: AnimationType,
    prevIndex: Int,
    selectedIndex: Int,
) {

    Box(
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    ) {

        val prevIndex = remember {
            mutableStateOf(0)
        }

//        val index = remember {
//            derivedStateOf{
//                mutableStateOf(index)
//            }
//        }

        val state = remember(isSelected, selectedIndex, prevSelectedIndex) {
            derivedStateOf {
                mutableStateOf(
                    if (isSelected) {
                        if (index == prevSelectedIndex) {
                            Destination.Nothing
                        } else
                            if (index < prevSelectedIndex) {
                                Destination.ToFromRight
                            } else {
                                Destination.ToFromLeft
                            }
                    } else {
                        if (index == selectedIndex) {
                            Destination.Nothing
                        } else
                            if (index < selectedIndex) {
                                Destination.FromToRight
                            } else {
                                Destination.FromToLeft
                            }
                    }
                )
            }
        }


        val isFromLeft by remember {
            derivedStateOf {
                val isFromLeft = mutableStateOf(prevIndex.value < index)
                prevIndex.value = index
                isFromLeft
            }
        }

        animationType.AnimatingIcon(
            modifier = modifier,
            isSelected = isSelected,
            isFromLeft = prevSelectedIndex < selectedIndex,
            isToLeft = selectedIndex < index,
            state = state.value,
            index = index,
            icon = icon,
            background = background,
            onClick = onClick
        )

        Log.e(
            "sommvoilnkfdbv",
            "index: ${index} selectedIndex:${selectedIndex} isFromLeft:${index < selectedIndex} isSelected:${isSelected}"
        )

    }
}