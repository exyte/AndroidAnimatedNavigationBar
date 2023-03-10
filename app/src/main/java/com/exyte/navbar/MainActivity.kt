package com.exyte.navbar

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.navbar.AnimatedNavigationBar
import com.exyte.navbar.navbar.animation.indendshape.Straight
import com.exyte.navbar.navbar.items.colorButtons.*
import com.exyte.navbar.navbar.items.wigglebutton.WiggleButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.NavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ElectricViolet)
                    ) {
                        val selectedItem = remember { mutableStateOf(0) }
                        val prevSelectedIndex = remember { mutableStateOf(0) }

//                        AnimatedNavigationBar(
//                            modifier = Modifier
//                                .align(Alignment.BottomCenter)
//                                .padding(horizontal = 8.dp, vertical = 20.dp)
//                                .height(80.dp),
//                            selectedIndex = selectedItem.value,
//                            ballColor = Color.White,
//                            cornerRadius = 30.dp,
//                            ballAnimation = Parabolic(tween(1000)),
//                            indentAnimation = Height(
//                                indentWidth = 60.dp,
//                                indentHeight = 20.dp,
//                                animationSpec = tween(1000)
//                            )
//                        ) {
//                            items.forEachIndexed { index, it ->
//                                DropletButton(
//                                    modifier = Modifier.fillMaxSize(),
//                                    isSelected = selectedItem.value == index,
//                                    onClick = { selectedItem.value = index },
//                                    icon = it.icon,
//                                    dropletColor = Purple,
//                                    animationSpec = tween(1000, easing = LinearEasing)
//                                )
//                            }
//                        }

//                        AnimatedNavigationBar(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .padding(horizontal = 8.dp, vertical = 20.dp)
//                                .height(72.dp),
//                            selectedIndex = selectedItem.value,
//                            ballColor = Color.White,
//                            cornerRadius = 20.dp,
//                            ballAnimation = com.exyte.navbar.navbar.animation.balltrajectory.Straight(
//                                tween(1000)
//                            ),
//                            indentAnimation = Straight(
//                                indentWidth = 50.dp,
//                                animationSpec = tween(1000)
//                            )
//                        ) {
//                            dropletButtonItems.forEachIndexed { index, it ->
//                                ColorButton(
//                                    modifier = Modifier
//                                        .fillMaxSize(1f),
//                                    isSelected = selectedItem.value == index,
//                                    prevSelectedIndex = prevSelectedIndex.value,
//                                    index = index,
//                                    selectedIndex = selectedItem.value,
//                                    onClick = {
//                                        prevSelectedIndex.value = selectedItem.value
//                                        selectedItem.value = index
//                                    },
//                                    icon = it.icon,
//                                    contentDescription = stringResource(id = it.description),
//                                    animationType = it.animationType,
//                                    background = it.animationType.background
//                                )
//                            }
//                        }

                        AnimatedNavigationBar(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 8.dp, vertical = 20.dp)
                                .height(72.dp),
                            selectedIndex = selectedItem.value,
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
                            wiggleButtonItems.forEachIndexed { index, it ->
                                WiggleButton(
                                    modifier = Modifier
                                        .fillMaxSize(1f),
                                    isSelected = selectedItem.value == index,
                                    onClick = {
                                        prevSelectedIndex.value = selectedItem.value
                                        selectedItem.value = index
                                    },
                                    icon = it.icon,
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

val wiggleButtonItems = listOf(
    Item(
        icon = R.drawable.outline_favorite,
        isSelected = false,
        description = R.string.Heart
    ),
    Item(
        icon = R.drawable.outline_energy_leaf,
        isSelected = false,
        description = R.string.Heart
    ),
    Item(
        icon = R.drawable.outline_water_drop,
        isSelected = false,
        description = R.string.Heart
    ),
    Item(
        icon = R.drawable.outline_circle,
        isSelected = false,
        description = R.string.Heart
    ),
    Item(
        icon = R.drawable.outline_laptop,
        isSelected = false,
        description = R.string.Heart
    ),
)

val items = listOf(
    Item(
        icon = R.drawable.home,
        isSelected = false,
        description = R.string.Home
    ),
    Item(
        icon = R.drawable.bell,
        isSelected = false,
        description = R.string.Bell
    ),
    Item(
        icon = R.drawable.message_buble,
        isSelected = false,
        description = R.string.Message
    ),
    Item(
        icon = R.drawable.heart,
        isSelected = false,
        description = R.string.Heart
    ),
    Item(
        icon = R.drawable.person,
        isSelected = false,
        description = R.string.Person
    ),
)

val dropletButtonItems = listOf(
    Item(
        icon = R.drawable.icon_home,
        isSelected = true,
        description = R.string.Home,
        animationType = BellColorButton(
            animationSpec = spring(
                dampingRatio = 7f,
                stiffness = 3000f
            ),
            background = ButtonBackground(
                icon = R.drawable.circle_background,
                offset = DpOffset(2.5.dp, 3.dp)
            ),
        )
    ),
    Item(
        icon = R.drawable.icon_bell,
        isSelected = false,
        description = R.string.Bell,
        animationType = BellColorButton(
            animationSpec =
//            tween(
//                700,
//                easing = { OvershootInterpolator(.2f).getInterpolation(it) }),
            spring(
                dampingRatio = 5f,
                stiffness = Spring.StiffnessMedium
            ),
            background = ButtonBackground(
                icon = R.drawable.rectangle_background,
                offset = DpOffset(1.dp, 2.dp)
            ),
        )
    ),
    Item(
        icon = R.drawable.icon_square,
        isSelected = false,
        description = R.string.Plus,
        animationType = PlusColorButton(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            ),
            background = ButtonBackground(
                icon = R.drawable.polygon_background,
                offset = DpOffset(1.6.dp, 0.5.dp)
            ),
        )
    ),
    Item(
        icon = R.drawable.icon_calendar,
        isSelected = false,
        description = R.string.Calendar,
        animationType = CalendarAnimation(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            ),
            background = ButtonBackground(
                icon = R.drawable.quadrangle_background,
                offset = DpOffset(1.dp, 1.5.dp)
            ),
        )
    ),
    Item(
        icon = R.drawable.icon_gear,
        isSelected = false,
        description = R.string.Settings,
        animationType = GearColorButton(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            ),
            background = ButtonBackground(
                icon = R.drawable.gear_background,
                offset = DpOffset(2.5.dp, 3.dp)
            ),
        )
    ),
)