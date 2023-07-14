package com.exyte.navbar

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.Purple
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
                Box(
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .size(80.dp)
                        .background(Color.White, shape = CircleShape)
                        .align(Alignment.BottomCenter),
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null
                    )
                }
                DropletButtonNavBar(
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun DropletButtonNavBar(modifier: Modifier) {
    var selectedItem by remember { mutableStateOf(0) }
    AnimatedNavigationBar(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 20.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            fabRadius = 60.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        DropletButton(
            modifier = Modifier.fillMaxSize(),
            isSelected = selectedItem == 0,
            onClick = { selectedItem = 0 },
            icon = dropletButtons[0].icon,
            dropletColor = Purple,
            animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
        )
        Box(Modifier.fillMaxSize())
        DropletButton(
            modifier = Modifier.fillMaxSize(),
            isSelected = selectedItem == 2,
            onClick = { selectedItem = 2 },
            icon = dropletButtons[1].icon,
            dropletColor = Purple,
            animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
        )
    }
}

const val Duration = 500
const val DoubleDuration = 1000