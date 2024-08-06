package com.exyte.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.colorButtons.BellColorButton
import com.exyte.navbar.colorButtons.ButtonBackground
import com.exyte.navbar.colorButtons.CalendarAnimation
import com.exyte.navbar.colorButtons.ColorButtonAnimation
import com.exyte.navbar.colorButtons.GearColorButton
import com.exyte.navbar.colorButtons.PlusColorButton
import kotlin.math.round

@Stable
data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: ColorButtonAnimation = BellColorButton(
        tween(500),
        background = ButtonBackground(R.drawable.plus)
    ),
    val route: String
)

val dropletButtons = listOf(
    Item(
        icon = R.drawable.home,
        isSelected = false,
        description = R.string.Home,
        route = "homescreen"
    ),
    Item(
        icon = R.drawable.bell,
        isSelected = false,
        description = R.string.Bell,
        route = "notificationscreen"
    ),
    Item(
        icon = R.drawable.message_buble,
        isSelected = false,
        description = R.string.Message,
        route = "messagescreen"
    ),
    Item(
        icon = R.drawable.heart,
        isSelected = false,
        description = R.string.Heart,
        route = "likescreen"
    ),
    Item(
        icon = R.drawable.person,
        isSelected = false,
        description = R.string.Person,
        route = "profilescreen"
    ),
)
