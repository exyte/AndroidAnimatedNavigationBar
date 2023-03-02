package com.exyte.navbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.exyte.navbar.navbar.BallAnimation
import com.exyte.navbar.navbar.IndentAnimation
import com.exyte.navbar.navbar.items.colorButtons.AnimationType
import com.exyte.navbar.navbar.items.colorButtons.BellAnimation
import com.exyte.navbar.navbar.items.colorButtons.ButtonBackground
import com.exyte.navbar.navbar.items.colorButtons.ColorButton
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

                        AnimatedNavigationBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 8.dp, vertical = 20.dp)
                                .height(72.dp),
                            selectedIndex = selectedItem.value,
                            ballColor = Color.White,
                            cornerRadius = 25.dp,
                            ballAnimation = BallAnimation.Parabolic(tween(1000)),
                            indentAnimation = IndentAnimation.Height(animationSpec = tween(1000))
                        ) {
                            items.forEachIndexed { index, it ->
                                WiggleButton(
                                    isSelected = selectedItem.value == index ,
                                    onClick = { selectedItem.value = index },
                                    icon = it.icon
                                )
//                                ColorButton(
//                                    modifier = Modifier
//                                        .fillMaxSize(1f),
//                                    isSelected = selectedItem.value == index,
//                                    prevSelectedIndex = prevSelectedIndex.value,
//                                    index = index,
//                                    selectedIndex = selectedItem.value,
//                                    prevIndex = prevSelectedIndex.value,
//                                    onClick = {
//                                        prevSelectedIndex.value = selectedItem.value
//                                        selectedItem.value = index
//                                    },
//                                    icon = it.icon,
//                                    contentDescription = stringResource(id = it.description),
//                                    animationType = it.animationType,
//                                    background = it.animationType.background
//                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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
        animationType = AnimationType.PlusAnimation(
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
        animationType = BellAnimation(
            animationSpec = spring(
                dampingRatio = 7f,
                stiffness = 3000f
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
        animationType = AnimationType.PlusAnimation(
            animationSpec = spring(
                dampingRatio = 7f,
                stiffness = 3000f
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
        animationType = AnimationType.CalendarAnimation(
            animationSpec = spring(
                dampingRatio = 7f,
                stiffness = 3000f
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
        animationType = AnimationType.GearAnimation(
            animationSpec = spring(
                dampingRatio = 7f,
                stiffness = 3000f
            ),
            background = ButtonBackground(
                icon = R.drawable.gear_background,
                offset = DpOffset(2.5.dp, 3.dp)
            ),
        )
    ),
)