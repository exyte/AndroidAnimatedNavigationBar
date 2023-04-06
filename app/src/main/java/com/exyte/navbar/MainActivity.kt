package com.exyte.navbar

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.isStatusBarVisible = false
                systemUiController.isNavigationBarVisible = false
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ElectricViolet)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    val animationIndex = remember { mutableStateOf(0) }
                    val prevAnimationIndex = remember { mutableStateOf(0) }
                    val itemsOrder = listOf(0, 1, 2, 4, 3, 0)

                    val finiteIntSequence = itemsOrder.asSequence()
                    val infiniteIntSequence = finiteIntSequence.repeat()
                    val iterator = infiniteIntSequence.take(100).iterator()

                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(1500)
                            prevAnimationIndex.value = animationIndex.value
                            animationIndex.value = iterator.next()
                        }
                    }

//                    ColorButtonNavBar(animationIndex.value, prevAnimationIndex.value)
//                    DropletButtonNavBar(animationIndex.value)
                    WiggleButtonNavBar(animationIndex.value)
                }
            }
        }
    }
}

@Composable
fun ColorButtonNavBar(anim: Int, prevAnim: Int) {
    val selectedItem = remember { mutableStateOf(0) }
    val prevSelectedIndex = remember { mutableStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 60.dp)
            .height(85.dp),
        selectedIndex = anim,
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
                prevSelectedIndex = prevAnim,
                selectedIndex = anim,
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
fun DropletButtonNavBar(value: Int) {
    val selectedItem = remember { mutableStateOf(0) }
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = value,
        ballColor = Color.White,
        cornerRadius = 25.dp,
        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        dropletButtons.forEachIndexed { index, it ->
            DropletButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = value == index,
                onClick = { selectedItem.value = index },
                icon = it.icon,
                dropletColor = Purple,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )
        }
    }
}

@Composable
fun WiggleButtonNavBar(value: Int) {
    val selectedItem = remember { mutableStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = value,
        ballColor = Color.White,
        cornerRadius = 25.dp,
        ballAnimation = Teleport(tween(Duration, easing = LinearEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        wiggleButtonItems.forEachIndexed { index, it ->
            WiggleButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = value == index,
                onClick = { selectedItem.value = index },
                icon = it.icon,
                backgroundIcon = it.backgroundIcon,
                wiggleColor = LightPurple,
                outlineColor = RoyalPurple,
                contentDescription = stringResource(id = it.description),
                enterExitAnimationSpec = tween(durationMillis = Duration, easing = LinearEasing),
                wiggleAnimationSpec = spring(dampingRatio = .45f, stiffness = 35f)
            )
        }
    }
}

const val Duration = 500
const val DoubleDuration = 1000