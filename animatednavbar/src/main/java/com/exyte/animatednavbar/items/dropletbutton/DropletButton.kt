package com.exyte.animatednavbar.items.dropletbutton

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.animatednavbar.items.wigglebutton.rememberVectorPainter
import com.exyte.animatednavbar.utils.noRippleClickable
import com.exyte.animatednavbar.utils.toPxf

/**
 *
 *A composable button that displays an icon with a droplet-shaped background. The button supports animation
 *and selection states.
 *@param modifier Modifier to be applied to the composable
 *@param isSelected Boolean representing whether the button is currently selected or not
 *@param onClick Callback to be executed when the button is clicked
 *@param icon Drawable resource of the icon to be displayed on the button
 *@param contentDescription A description for the button to be used by accessibility
 *@param iconColor Color to tint the icon
 *@param dropletColor Color of the droplet-shaped background
 *@param size Icon size
 *@param animationSpec Animation specification to be used when animating the button
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropletButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String? = null,
    iconColor: Color = Color.LightGray,
    dropletColor: Color = Color.Red,
    size: Dp = 20.dp,
    animationSpec: AnimationSpec<Float> = remember { tween(300) }
) {
    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

    val vector = ImageVector.vectorResource(id = icon)
    val painter = rememberVectorPainter(image = vector, blendMode = BlendMode.DstOver)

    Box(
        modifier = modifier
            .onGloballyPositioned {
                canvasSize = it.size.toSize()
            }
            .noRippleClickable {
                onClick()
            }
    ) {
        val density = LocalDensity.current

        val dropletButtonParams = animateDropletButtonAsState(
            isSelected = isSelected, animationSpec = animationSpec, size = size.toPxf(density)
        )

        val iconOffset by remember(size,canvasSize) {
            derivedStateOf {
                mutableStateOf((canvasSize.height - size.toPxf(density))/2)
            }
        }
        Canvas(
            modifier = Modifier
                .width(size)
                .fillMaxHeight()
                .align(Alignment.Center)
                .graphicsLayer(
                    alpha = 0.99f,
                    scaleX = dropletButtonParams.value.scale,
                    scaleY = dropletButtonParams.value.scale,
                )
                .onGloballyPositioned {
                    canvasSize = it.size.toSize()
                },
            contentDescription = contentDescription?:""
        ) {
            translate(left = 0f, top = iconOffset.value) {
                with(painter) {
                    draw(
                        size = Size(size.toPxf(density), size.toPxf(density)),
                        colorFilter = ColorFilter.tint(color = iconColor)
                    )
                }
            }

            drawCircle(
                color = dropletColor,
                radius = dropletButtonParams.value.radius,
                center = Offset(
                    canvasSize.width / 2,
                    dropletButtonParams.value.verticalOffset + iconOffset.value - 20f
                ),
                blendMode = BlendMode.SrcIn
            )
        }
    }
}