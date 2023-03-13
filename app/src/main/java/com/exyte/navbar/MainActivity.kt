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
import com.exyte.navbar.navbar.AnimatedNavigationBar
import com.exyte.navbar.navbar.animation.balltrajectory.Parabolic
import com.exyte.navbar.navbar.animation.balltrajectory.Teleport
import com.exyte.navbar.navbar.animation.indendshape.Height
import com.exyte.navbar.navbar.animation.indendshape.Straight
import com.exyte.navbar.navbar.items.colorButtons.*
import com.exyte.navbar.navbar.items.dropletbutton.DropletButton
import com.exyte.navbar.navbar.items.wigglebutton.WiggleButton
import com.exyte.navbar.navbar.utils.noRippleClickable
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
                        val itemsOrder = listOf(0, 1, 2, 4, 3, 0)

                        val finiteIntSequence = itemsOrder.asSequence()
                        val infiniteIntSequence = finiteIntSequence.repeat()
                        val iterator = infiniteIntSequence.take(100).iterator()

                        var i = itemsOrder.iterator()
                        LaunchedEffect(replay.value) {
                            while (replay.value) {
                                delay(1000)
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
                                    .padding(horizontal = 8.dp, vertical = 20.dp)
                                    .height(72.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 20.dp,
                                ballAnimation = com.exyte.navbar.navbar.animation.balltrajectory.Straight(
                                    tween(1000)
                                ),
                                indentAnimation = Straight(
                                    indentWidth = 50.dp,
                                    animationSpec = tween(1000)
                                )
                            ) {
                                dropletButtonItems.forEachIndexed { index, it ->
                                    ColorButton(
                                        modifier = Modifier
                                            .fillMaxSize(1f),
                                        isSelected = animationIndex.value == index,
                                        prevSelectedIndex = prevSelectedIndex.value,
                                        index = index,
                                        selectedIndex = selectedItem.value,
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
                                    .padding(horizontal = 8.dp, vertical = 20.dp)
                                    .height(72.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 10.dp,
                                ballAnimation = Parabolic(tween(1000)),
                                indentAnimation = Height(
                                    indentWidth = 60.dp,
                                    indentHeight = 20.dp,
                                    animationSpec = tween(1000)
                                )
                            ) {
                                items.forEachIndexed { index, it ->
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
                                    .padding(horizontal = 8.dp, vertical = 20.dp)
                                    .height(72.dp),
                                selectedIndex = animationIndex.value,
                                ballColor = Color.White,
                                cornerRadius = 20.dp,
                                ballAnimation = Teleport(
                                    tween(1000)
                                ),
                                indentAnimation = Height(
                                    indentWidth = 50.dp,
                                    animationSpec = tween(1000)
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