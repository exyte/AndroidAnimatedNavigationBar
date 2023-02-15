package com.exyte.navbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.exyte.navbar.navbar.AnimatedNavigationBar
import com.exyte.navbar.navbar.AnimatedNavigationItem
import com.exyte.navbar.ui.theme.NavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box() {
                        val selectedItem = remember { mutableStateOf(0) }

                        AnimatedNavigationBar(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            selectedIndex = selectedItem.value,
                        ) {
                            items.forEachIndexed { index,it ->
                                AnimatedNavigationItem(
                                    isSelected = selectedItem.value == index,
                                    onClick = { selectedItem.value = index },
                                    icon = it.icon,
                                    description = stringResource(id = it.description)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun NavBarExample(
//){
//    AnimatedNavigationBar(
//        modifier = Modifier.align(Alignment.BottomCenter)
//    ) {
//        items.forEach {
//            AnimatedNavigationItem(
//                selected = it.isSelected,
//                onClick = { /*TODO*/ },
//                icon = it.icon,
//                description = stringResource(id = it.description)
//            )
//        }
//    }
//}

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavBarTheme {

    }
}