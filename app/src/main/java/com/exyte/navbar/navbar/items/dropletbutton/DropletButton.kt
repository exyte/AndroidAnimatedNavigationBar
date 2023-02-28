@file:OptIn(ExperimentalFoundationApi::class)

package com.exyte.navbar.navbar.items.dropletbutton

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.navbar.R
import com.exyte.navbar.navbar.items.wigglebutton.rememberVectorPainter
import com.exyte.navbar.navbar.noRippleClickable
import com.exyte.navbar.navbar.toPxf
import com.exyte.navbar.ui.theme.LightGray

@Composable
fun DropletButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String = "",
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    selectedColor: Color = Color.Black,
    iconColor: Color = Color.LightGray,
    dropletColor: Color = Color.Red
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

        val dropletButtonParams = animateDropletButtonAsState(isSelected = isSelected)

        val density = LocalDensity.current

//        Image(
//            modifier = Modifier
//                .graphicsLayer {
//                    alpha = 0.99f
//                }
//                .align(Alignment.Center),
//            painter = painterResource(id = icon),
//            contentDescription = contentDescription,
//            colorFilter = ColorFilter.tint(iconColor, blendMode = BlendMode.SrcIn)
//        )
//
//        Image(
//            modifier = Modifier
//                .offset(x = 20.dp, y = 30.dp),
//            painter = painterResource(id = icon),
//            contentDescription = null,
//            colorFilter = ColorFilter.tint(dropletColor)
//        )

//        Box(
//            modifier = Modifier.drawWithContent {
//                drawCircle(
//                    color = dropletColor,
//                    radius = dropletButtonParams.value.radius,
//                    center = Offset(canvasSize.width / 2, dropletButtonParams.value.verticalOffset),
//                    blendMode = BlendMode.SrcAtop
//                )
//            }
//        )

        val iconOffset by remember {
            derivedStateOf{
                mutableStateOf((canvasSize.height - 20.dp.toPxf(density))/2)
            }
        }
//
        Canvas(
            modifier = Modifier
//                .size(20.dp,20.dp)
                .width(20.dp)
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
            contentDescription = contentDescription
        ) {
            translate(left = 0f, top = iconOffset.value) {
                with(painter) {
                    draw(
                        size = Size(20.dp.toPxf(density), 20.dp.toPxf(density)),
//                    Size(canvasSize.width, canvasSize.width),
                        colorFilter = ColorFilter.tint(color = iconColor)
                    )
                }
            }

            drawCircle(
                color = dropletColor,
                radius = dropletButtonParams.value.radius,
                center = Offset(canvasSize.width / 2, dropletButtonParams.value.verticalOffset + iconOffset.value),
                blendMode = BlendMode.SrcIn
            )
        }
    }
}

//fun DrawScope.draw(
//    size: Size,
//    alpha: Float = DefaultAlpha,
//    colorFilter: ColorFilter? = null
//) {
//    configureAlpha(alpha)
//    configureColorFilter(colorFilter)
//    configureLayoutDirection(layoutDirection)
//
//    // b/156512437 to expose saveLayer on DrawScope
//    inset(
//        left = 0.0f,
//        top = 0.0f,
//        right = this.size.width - size.width,
//        bottom = this.size.height - size.height
//    ) {
//
//        if (alpha > 0.0f && size.width > 0 && size.height > 0) {
//            if (useLayer) {
//                val layerRect = Rect(Offset.Zero, Size(size.width, size.height))
//                // TODO (b/154550724) njawad replace with RenderNode/Layer API usage
//                drawIntoCanvas { canvas ->
//                    canvas.withSaveLayer(layerRect, obtainPaint()) {
//                        onDraw()
//                    }
//                }
//            } else {
//                onDraw()
//            }
//        }
//    }
//}