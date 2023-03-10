package com.exyte.navbar.navbar.items.dropletbutton

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.exyte.navbar.navbar.items.wigglebutton.rememberVectorPainter
import com.exyte.navbar.navbar.utils.noRippleClickable
import com.exyte.navbar.navbar.utils.toPxf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropletButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String = "",
    iconColor: Color = Color.LightGray,
    dropletColor: Color = Color.Red,
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

        val animSpec = remember { animationSpec }
        val dropletButtonParams = animateDropletButtonAsState(
            isSelected = isSelected, animationSpec = animSpec
        )

        val density = LocalDensity.current

        val iconOffset by remember {
            derivedStateOf {
                mutableStateOf((canvasSize.height - 20.dp.toPxf(density)) / 2)
            }
        }
        Canvas(
            modifier = Modifier
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