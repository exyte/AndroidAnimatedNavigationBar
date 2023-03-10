package com.exyte.navbar.navbar.items.wigglebutton

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.navbar.navbar.utils.noRippleClickable
import com.exyte.navbar.ui.theme.LightGray
import kotlin.math.PI

@Composable
fun WiggleButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    @DrawableRes backgroundIcon: Int,
    contentDescription: String = "",
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = LightGray,
    backgroundIconColor: Color = Color.White,
    arcColor: Color = Color.Blue,
    iconSize: Dp = 25.dp,
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
            arcColor = arcColor,
            iconColor = backgroundIconColor,
            backgroundIconColor = backgroundIconColor,
            contentDescription = contentDescription,
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
    contentDescription: String,
    arcColor: Color,
    iconColor: Color,
    backgroundIconColor: Color,
) {

    val vector = ImageVector.vectorResource(id = icon)
    val painter = rememberVectorPainter(image = vector, blendMode = BlendMode.SrcIn)

    val backgroundVector = ImageVector.vectorResource(id = backgroundIcon)
    val backgroundPainter =
        rememberVectorPainter(image = backgroundVector, blendMode = BlendMode.SrcIn)

    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }

//    val radius = animateFloatAsState(
//        targetValue = if (isSelected) 30f else 10f
//    )

    val wiggleButtonParams = animateWiggleButtonAsState(isSelected = isSelected)

    val offset by remember {
        derivedStateOf {
            mutableStateOf(
                Offset(
                    x = (canvasSize.width - 2 * wiggleButtonParams.value.radius) / 2,
                    y = canvasSize.height - wiggleButtonParams.value.radius
                )
            )

        }
    }

    Log.e("offset", "${wiggleButtonParams.value.alpha}")

    val path = Path()
        .apply {
            moveTo(0f, 0f)
            addArcRad(
                oval = Rect(
                    offset = Offset(x = offset.value.x, y = offset.value.y),
                    size = Size(
                        width = 2 * wiggleButtonParams.value.radius,
                        height = 2 * wiggleButtonParams.value.radius
                    )
                ),
                PI.toFloat(),
                PI.toFloat()
            )
        }

    Canvas(
        modifier = modifier
            .graphicsLayer(
                alpha = 0.99f,
                scaleX = wiggleButtonParams.value.scale,
                scaleY = wiggleButtonParams.value.scale
            )
            .alpha(alpha = wiggleButtonParams.value.alpha)
            .fillMaxSize()
            .onGloballyPositioned {
                canvasSize = it.size.toSize()
            },
        contentDescription = contentDescription
    ) {

        with(backgroundPainter) {
            draw(
                size = Size(canvasSize.width, canvasSize.width),
                colorFilter = ColorFilter.tint(color = backgroundIconColor)
            )
        }

        drawPath(path, color = arcColor, blendMode = BlendMode.SrcIn)

        with(painter) {
            draw(
                size = Size(canvasSize.width, canvasSize.width),
                colorFilter = ColorFilter.tint(color = Color(0xFF64419F))
            )
        }
    }
}

@Composable
fun rememberVectorPainter(image: ImageVector, blendMode: BlendMode) =
    rememberVectorPainter(
        defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = image.tintColor,
        tintBlendMode = blendMode,
        autoMirror = image.autoMirror,
        content = { _, _ -> RenderVectorGroup(group = image.root) }
    )