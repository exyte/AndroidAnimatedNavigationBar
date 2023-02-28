package com.exyte.navbar.navbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.animation.shape.ShapeInfo
import com.exyte.navbar.navbar.paths.IndentRectShape
import com.exyte.navbar.navbar.paths.IndentShapeData

sealed class IndentAnimation(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(1000),
    open val indentWidth: Dp = 50.dp,
    open val indentHeight: Dp = 20.dp,
) {

    @Composable
    abstract fun animateIndentShapeAsState(toOffset: Offset, shapeInfo: ShapeInfo): State<Shape>

    data class Straight(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val indentWidth: Dp = 50.dp,
        override val indentHeight: Dp = 20.dp,
    ) : IndentAnimation(animationSpec, indentHeight, indentWidth) {

        @Composable
        override fun animateIndentShapeAsState(
            toOffset: Offset,
            shapeInfo: ShapeInfo
        ): State<Shape> {
            val density = LocalDensity.current

            val indentShapeData by remember {
                derivedStateOf {
                    mutableStateOf(
                        IndentShapeData(
                            xIndent = toOffset.x,
                            height = indentHeight.toPxf(density),
                            ballSize = ballSize.toPxf(density) / 2f,
                            width = indentWidth.toPxf(density),
                            cornerRadius = shapeInfo.cornerRadius.toPxf(density)
                        )
                    )
                }
            }

            var shape by remember {
                mutableStateOf(
                    IndentRectShape(
                        indentShapeData = indentShapeData.value
                    )
                )
            }

            val position = animateFloatAsState(
                targetValue = toOffset.x,
                animationSpec = animationSpec
            )

            return remember {
                derivedStateOf {
                    shape = shape.copy(xIndent = position.value)
                    shape
                }
            }
        }
    }

    data class Height(
        override val animationSpec: FiniteAnimationSpec<Float>,
        override val indentWidth: Dp = 50.dp,
        override val indentHeight: Dp = 20.dp,
    ) : IndentAnimation(animationSpec, indentHeight, indentWidth) {

        @Composable
        override fun animateIndentShapeAsState(
            toOffset: Offset,
            shapeInfo: ShapeInfo
        ): State<Shape> {
            var from by remember { mutableStateOf(Offset.Zero) }
            var to by remember { mutableStateOf(Offset.Zero) }
            val fraction = remember { Animatable(0f) }

            val density = LocalDensity.current

            val yIndentStart = 20.dp.toPxf()
            var yIndent by remember { mutableStateOf(yIndentStart) }

            val indentShapeData by remember {
                derivedStateOf {
                    mutableStateOf(
                        IndentShapeData(
                            xIndent = toOffset.x + shapeInfo.layoutOffset.x - ballSize.toPxf(density),
                            height = indentHeight.toPxf(density),
                            ballSize = ballSize.toPxf(density),
                            width = indentWidth.toPxf(density),
                            cornerRadius = shapeInfo.cornerRadius.toPxf(density)
                        )
                    )
                }
            }

            val shape by remember {
                derivedStateOf {
                    mutableStateOf(
                        IndentRectShape(
                            indentShapeData = indentShapeData.value
                        )
                    )
                }
            }

            LaunchedEffect(toOffset) {
                if (to != toOffset) {
                    from = to
                    to = toOffset

                    fraction.snapTo(0f)
                    fraction.animateTo(2f, animationSpec)
                }
            }

            val ballSizePx = ballSize.toPx()

            return remember {
                derivedStateOf {
                    if (fraction.value <= 1f) {
                        yIndent = lerp(yIndentStart, 0f, fraction.value)
                        indentShapeData.value = IndentShapeData(
                            cornerRadius = indentShapeData.value.cornerRadius,
                            xIndent = from.x,
                            height = yIndent,
                            width = indentShapeData.value.width,
                            ballSize = indentShapeData.value.ballSize
                        )
                        shape.value
                    } else {
                        yIndent = lerp(0f, yIndentStart, fraction.value - 1f)
                        indentShapeData.value = IndentShapeData(
                            cornerRadius = indentShapeData.value.cornerRadius,
                            xIndent = to.x,
                            height = yIndent,
                            width = indentShapeData.value.width,
                            ballSize = indentShapeData.value.ballSize
                        )
                        shape.value
                    }
                }
            }
        }
    }
}