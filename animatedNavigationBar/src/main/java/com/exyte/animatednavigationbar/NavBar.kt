//package com.exyte.animatednavigationbar
//
//import androidx.annotation.DrawableRes
//import androidx.annotation.StringRes
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun AnimatedNavigationBar(
//    modifier: Modifier = Modifier,
//    items: List<NavigationItem>,
//    content: @Composable RowScope.() -> Unit,
//) {
//
//
////    Box(
////        modifier = modifier
////            .fillMaxWidth()
////    ) {
////        val rememberItems = remember {
////            items
////        }
//
//        Row(
//            modifier = modifier,
//            content = content,
//        )
//
////            rememberItems.forEach { item ->
////
//////                val isSelected =
//////                    currentDestination?.hierarchy?.any { it.route == item.route } == true
////
//////                fun navigate() {
//////                    if (isSelected) {
//////                        navController.navigate(item.route) {
//////                            launchSingleTop = true
//////                        }
//////                        return
//////                    }
//////                }
////
////                BottomNavigationItem(
////                    modifier = Modifier.size(81.dp),
////                    selected = item.isSelected,
////                    onClick = {
////                        item.isSelected = !item.isSelected
////                    },
////                    icon = item.icon,
////                    description = stringResource(id = item.description)
////                )
////            }
////        }
//}
//
//sealed class NavigationItem(
//    val route: String,
//    @DrawableRes val icon: Int,
//    var isSelected: Boolean,
//    @StringRes val label: Int,
//    @StringRes val description: Int,
//) {
//
//}
//
//val selectedColor = Color(0xFFE9A75A)
//
//val backgroundColor = Color(0xFFEEEBE7)
//val accentColor = Color(0xFF625E5C)
////val backgroundColor = Color.Black
//
//@Composable
//fun RowScope.AnimatedNavigationItem(
//    selected: Boolean,
//    onClick: () -> Unit,
//    icon: Int,
////    icon: @Composable () -> Unit,
//    description: String,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: @Composable (() -> Unit)? = null,
//    alwaysShowLabel: Boolean = true,
//) {
//    val isSelected = remember { mutableStateOf(selected) }
//    val height = animateDpAsState(
//        targetValue = if (isSelected.value) 73.dp else 65.dp,
//        animationSpec = tween(500)
//    )
//    val tintColor = animateColorAsState(
//        targetValue = if (isSelected.value) androidx.compose.ui.graphics.Color.White else accentColor,
//        animationSpec = tween(500)
//    )
//    val iconBackgroundColor = animateColorAsState(
//        targetValue = if (isSelected.value) selectedColor else backgroundColor,
//        animationSpec = tween(500)
//    )
//    Box(
//        modifier = modifier
//            .noRippleClickable {
//                isSelected.value = !isSelected.value
//                onClick()
//            }
//            .weight(1f)
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .fillMaxSize()
//                .align(Alignment.BottomCenter)
//                .background(color = backgroundColor)
//        )
//        Box(
//            modifier = Modifier
//                .padding(bottom = 8.dp)
//                .size(73.dp, height.value)
//                .align(Alignment.BottomCenter)
//                .clip(CircleShape)
//                .background(color = backgroundColor)
//        )
//        Box(
//            modifier = Modifier
//                .padding(bottom = 16.dp)
//                .size(48.dp)
//                .align(Alignment.BottomCenter)
//                .clip(CircleShape)
//                .background(color = iconBackgroundColor.value)
//        ) {
//
//            Icon(
//                modifier = Modifier
//                    .align(Alignment.Center),
//                painter = painterResource(id = icon),
//                contentDescription = description,
//                tint = tintColor.value
//            )
//        }
//    }
//}
//
////@Composable
////@Preview
////private fun PreviewBottomBar(
////    items: List<NavigationItem> = listOf(
////        NavigationItem.Home,
////        NavigationItem.Routes,
////        NavigationItem.Map,
////        NavigationItem.Favorite,
////        NavigationItem.Profile
////    ),
////) {
////    CrossiNavigationBar(
////        navController = rememberNavController(),
////        items = items,
////    )
////}