package com.exyte.navbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.exyte.animatednavbar.utils.noRippleClickable
import com.exyte.navbar.colorButtons.ColorButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.NavBarTheme
import com.exyte.navbar.ui.theme.Purple
import kotlinx.coroutines.delay

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavBarTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val replay = remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .noRippleClickable { replay.value = !replay.value }
                            .fillMaxSize()
                            .background(ElectricViolet)
                    ) {

                        val animationIndex = remember { mutableStateOf(0) }
                        val prevAnimationIndex = remember { mutableStateOf(0) }
                        val itemsOrder = listOf(0, 1, 2, 4, 3, 0)

                        val finiteIntSequence = itemsOrder.asSequence()
                        val infiniteIntSequence = finiteIntSequence.repeat()
                        val iterator = infiniteIntSequence.take(100).iterator()

                        LaunchedEffect(replay.value) {
                            while (replay.value) {
                                delay(1500)
                                prevAnimationIndex.value = animationIndex.value
                                animationIndex.value = iterator.next()
                            }
                        }

                        val selectedItem = remember { mutableStateOf(0) }
                        val prevSelectedIndex = remember { mutableStateOf(0) }

                        Spacer(
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .weight(1f)
                        )

                        Column(
                            modifier = Modifier
                        ) {
                            AnimatedNavigationBar(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 40.dp)
                                    .height(85.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 25.dp,
                                ballAnimation = Straight(
                                    spring(
                                        dampingRatio = 0.6f,
                                        stiffness = Spring.StiffnessVeryLow
                                    )
                                ),
                                indentAnimation = StraightIndent(
                                    indentWidth = 56.dp,
                                    indentHeight = 15.dp,
                                    animationSpec = tween(1000)
                                )
                            ) {
                                dropletButtonItems.forEachIndexed { index, it ->
                                    ColorButton(
                                        modifier = Modifier
                                            .fillMaxSize(1f),
                                        isSelected = animationIndex.value == index,
                                        prevSelectedIndex = prevAnimationIndex.value,
                                        index = index,
                                        selectedIndex = animationIndex.value,
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

                            AnimatedNavigationBar(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 40.dp)
                                    .height(85.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 25.dp,
                                ballAnimation = Parabolic(tween(1000)),
                                indentAnimation = Height(
                                    indentWidth = 56.dp,
                                    indentHeight = 15.dp,
                                    animationSpec = tween(1000)
                                )
                            ) {
                                dropletButton.forEachIndexed { index, it ->
                                    DropletButton(
                                        modifier = Modifier.fillMaxSize(),
                                        isSelected = animationIndex.value == index,
                                        onClick = { selectedItem.value = index },
                                        icon = it.icon,
                                        dropletColor = Purple,
                                        animationSpec = tween(1000, easing = LinearEasing)
                                    )
                                }
                            }

                            AnimatedNavigationBar(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 40.dp)
                                    .height(85.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 25.dp,
                                ballAnimation = Teleport(
                                    tween(1000, easing = LinearEasing)
                                ),
                                indentAnimation = Height(
                                    indentWidth = 56.dp,
                                    indentHeight = 15.dp,
                                    animationSpec = tween(1000, easing = LinearEasing)
                                )
                            ) {
                                wiggleButtonItems.forEachIndexed { index, it ->
                                    WiggleButton(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        isSelected = animationIndex.value == index,
                                        onClick = {
                                            prevSelectedIndex.value = selectedItem.value
                                            selectedItem.value = index
                                        },
                                        icon = it.icon,
                                        backgroundIcon = it.backgroundIcon,
                                        arcColor = Color(0xFFBCA1E7),
                                        contentDescription = stringResource(id = it.description),
                                        enterExitAnimationSpec = tween(500, easing = LinearEasing),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}