package com.exyte.animatednavbar.items.wigglebutton

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.animatednavbar.utils.noRippleClickable
import com.exyte.animatednavbar.utils.toPxf

/**
 *
 *A composable button that displays an icon with a wiggle part background.
 *
 *@param modifier  Modifier to be applied to the composable
 *@param isSelected Boolean value indicating whether the view is selected or not
 *@param onClick Callback to be executed when the button is clicked
 *@param icon Drawable resource of the ImageVector resource used for the icon
 *@param backgroundIcon Drawable resource of the ImageVector resource used for the background icon
 *@param contentDescription Content description used to describe the view for accessibility purposes
 *@param wiggleColor Color of the arc drawn on top of the view
 *@param iconSize Icon size
 *@param backgroundIconColor Color of the background icon in the view,
 *@param outlineColor Outline icon color
 *@param enterExitAnimationSpec Animation spec for appearing/disappearing
 *@param wiggleAnimationSpec Animation spec for wiggle effect
 */
@Composable
fun WiggleButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    @DrawableRes backgroundIcon: Int,
    contentDescription: String? = null,
    backgroundIconColor: Color = Color.White,
    wiggleColor: Color = Color.Blue,
    outlineColor: Color = Color.LightGray,
    iconSize: Dp = 25.dp,
    enterExitAnimationSpec: AnimationSpec<Float> = spring(),
    wiggleAnimationSpec: AnimationSpec<Float> =
        spring(dampingRatio = 0.6f, stiffness = 35f)
) {

    Box(
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    ) {

        DrawWithBlendMode(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            icon = icon,
            backgroundIcon = backgroundIcon,
            isSelected = isSelected,
            wiggleColor = wiggleColor,
            backgroundIconColor = backgroundIconColor,
            outlineColor = outlineColor,
            contentDescription = contentDescription,
            enterExitAnimationSpec = enterExitAnimationSpec,
            wiggleAnimationSpec = wiggleAnimationSpec,
            size = iconSize,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawWithBlendMode(
    modifier: Modifier,
    isSelected: Boolean,
    @DrawableRes icon: Int,
    @DrawableRes backgroundIcon: Int,
    contentDescription: String? = null,
    wiggleColor: Color,
    size: Dp,
    backgroundIconColor: Color,
    enterExitAnimationSpec: AnimationSpec<Float>,
    wiggleAnimationSpec: AnimationSpec<Float>,
    outlineColor: Color,
) {

    val vector = ImageVector.vectorResource(id = icon)
    val painter = rememberVectorPainter(image = vector)

    val backgroundVector = ImageVector.vectorResource(id = backgroundIcon)
    val backgroundPainter = rememberVectorPainter(image = backgroundVector)

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    val density = LocalDensity.current
    val sizePx = remember(size) { size.toPxf(density) }

    val wiggleButtonParams = animateWiggleButtonAsState(
        isSelected = isSelected,
        enterExitAnimationSpec = enterExitAnimationSpec,
        wiggleAnimationSpec = wiggleAnimationSpec,
        maxRadius = sizePx
    )

    val offset by remember {
        derivedStateOf {
            mutableStateOf(
                Offset(x = canvasSize.width / 2, y = canvasSize.height)
            )
        }
    }

    Canvas(
        modifier = modifier
            .graphicsLayer(
                alpha = wiggleButtonParams.value.alpha,
                scaleX = wiggleButtonParams.value.scale,
                scaleY = wiggleButtonParams.value.scale
            )
            .fillMaxSize()
            .onGloballyPositioned { canvasSize = it.size.toSize() },
        contentDescription = contentDescription ?: ""
    ) {

        with(backgroundPainter) {
            draw(
                size = Size(sizePx, sizePx),
                colorFilter = ColorFilter.tint(color = backgroundIconColor)
            )
        }

        drawCircle(
            color = wiggleColor,
            center = offset.value,
            radius = wiggleButtonParams.value.radius,
            blendMode = BlendMode.SrcIn
        )

        with(painter) {
            draw(
                size = Size(sizePx, sizePx),
                colorFilter = ColorFilter.tint(color = outlineColor)
            )
        }
    }
}