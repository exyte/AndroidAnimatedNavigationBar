package com.exyte.navbar

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.screens.HomeScreen
import com.exyte.navbar.ui.theme.screens.LikesScreen
import com.exyte.navbar.ui.theme.screens.MessagesScreen
import com.exyte.navbar.ui.theme.screens.NotificationScreen
import com.exyte.navbar.ui.theme.screens.ProfileScreen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.isStatusBarVisible = false
                systemUiController.isNavigationBarVisible = false
            }
            PreviewScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PreviewScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ElectricViolet),
        bottomBar = {
            DropletButtonNavBar(navController)
        }
    ) {
        NavHost(navController = navController, startDestination="homescreen"){
            composable("homescreen"){HomeScreen()}
            composable("likescreen"){ LikesScreen() }
            composable("messagescreen"){ MessagesScreen() }
            composable("notificationscreen"){ NotificationScreen() }
            composable("profilescreen"){ ProfileScreen() }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceType")
@Composable
fun DropletButtonNavBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 20.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Teleport(),
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
                isSelected = selectedItem == index,
                onClick = { selectedItem = index
                            navController.navigate(it.route){
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                          },
                icon = it.icon,
                dropletColor = ElectricViolet,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )
        }

    }
}


const val Duration = 500
const val DoubleDuration = 1000