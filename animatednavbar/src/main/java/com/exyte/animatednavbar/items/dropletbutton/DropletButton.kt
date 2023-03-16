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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = modifier.noRippleClickable { onClick() }
    ) {
        val density = LocalDensity.current
        val dropletButtonParams = animateDropletButtonAsState(
            isSelected = isSelected, animationSpec = animationSpec, size = size.toPxf(density)
        )

        val sizePx = remember(size) { size.toPxf(density) }
        val circleCenter by remember {
            derivedStateOf {
                mutableStateOf(sizePx / 2)
            }
        }

        val vector = ImageVector.vectorResource(id = icon)
        val painter = rememberVectorPainter(image = vector)
        Canvas(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center)
                .graphicsLayer(
                    alpha = 0.99f,
                    scaleX = dropletButtonParams.value.scale,
                    scaleY = dropletButtonParams.value.scale,
                ),
            contentDescription = contentDescription ?: ""
        ) {
            with(painter) {
                draw(
                    size = Size(sizePx, sizePx),
                    colorFilter = ColorFilter.tint(color = iconColor)
                )
            }

            drawCircle(
                color = dropletColor,
                radius = dropletButtonParams.value.radius,
                center = Offset(
                    circleCenter.value,
                    dropletButtonParams.value.verticalOffset - 20f
                ),
                blendMode = BlendMode.SrcIn
            )
        }
    }
}