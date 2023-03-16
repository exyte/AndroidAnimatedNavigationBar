package com.exyte.navbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.StraightIndent
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.exyte.animatednavbar.items.wigglebutton.WiggleButton
import com.exyte.navbar.colorButtons.ColorButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.LightPurple
import com.exyte.navbar.ui.theme.Purple
import com.exyte.navbar.ui.theme.RoyalPurple

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ElectricViolet)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    ColorButtonNavBar()
                    DropletButtonNavBar()
                    WiggleButtonNavBar()
                }
            }
        }
    }
}

@Composable
fun ColorButtonNavBar() {
    val selectedItem = remember { mutableStateOf(0) }
    val prevSelectedIndex = remember { mutableStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = selectedItem.value,
        ballColor = Color.White,
        cornerRadius = 25.dp,
        ballAnimation = Straight(
            spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessVeryLow)
        ),
        indentAnimation = StraightIndent(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000)
        )
    ) {
        colorButtons.forEachIndexed { index, it ->
            ColorButton(
                modifier = Modifier.fillMaxSize(),
                prevSelectedIndex = prevSelectedIndex.value,
                selectedIndex = selectedItem.value,
                index = index,
                onClick = {
                    prevSelectedIndex.value = selectedItem.value
                    selectedItem.value = index
                },
                icon = it.icon,
                contentDescription = stringResource(id = it.description),
                animationType = it.animationType,
                background = it.animationType.background
            )
        }
    }
}

@Composable
fun DropletButtonNavBar() {
    val selectedItem = remember { mutableStateOf(0) }
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = selectedItem.value,
        ballColor = Color.White,
        cornerRadius = 25.dp,
        ballAnimation = Parabolic(tween(1000)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000)
        )
    ) {
        dropletButtons.forEachIndexed { index, it ->
            DropletButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem.value == index,
                onClick = { selectedItem.value = index },
                icon = it.icon,
                dropletColor = Purple,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}

@Composable
fun WiggleButtonNavBar() {
    val selectedItem = remember { mutableStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = selectedItem.value,
        ballColor = Color.White,
        cornerRadius = 25.dp,
        ballAnimation = Teleport(tween(1000, easing = LinearEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000, easing = LinearEasing)
        )
    ) {
        wiggleButtonItems.forEachIndexed { index, it ->
            WiggleButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem.value == index,
                onClick = { selectedItem.value = index },
                icon = it.icon,
                backgroundIcon = it.backgroundIcon,
                wiggleColor = LightPurple,
                outlineColor = RoyalPurple,
                contentDescription = stringResource(id = it.description),
                enterExitAnimationSpec = tween(durationMillis = 500, easing = LinearEasing),
            )
        }
    }
}