package com.exyte.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.exyte.navbar.colorButtons.*

@Stable
data class WiggleButtonItem(
    @DrawableRes val backgroundIcon:Int,
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: ColorButtonAnimation = BellColorButton(
        tween(500),
        background = ButtonBackground(R.drawable.icon_plus)
    ),
)

@Stable
data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: ColorButtonAnimation = BellColorButton(
        tween(500),
        background = ButtonBackground(R.drawable.icon_plus)
    ),
)

val wiggleButtonItems = listOf(
    WiggleButtonItem(
        icon = R.drawable.outline_favorite,
        backgroundIcon = R.drawable.icon_favorite,
        isSelected = false,
        description = R.string.Heart
    ),
    WiggleButtonItem(
        icon = R.drawable.outline_energy_leaf,
        backgroundIcon = R.drawable.icon_energy_savings_leaf,
        isSelected = false,
        description = R.string.Heart
    ),
    WiggleButtonItem(
        icon = R.drawable.outline_water_drop,
        backgroundIcon = R.drawable.water_drop_icon,
        isSelected = false,
        description = R.string.Heart
    ),
    WiggleButtonItem(
        icon = R.drawable.outline_circle,
        backgroundIcon = R.drawable.icon_circle,
        isSelected = false,
        description = R.string.Heart
    ),
    WiggleButtonItem(
        icon = R.drawable.baseline_laptop,
        backgroundIcon = R.drawable.icon_laptop,
        isSelected = false,
        description = R.string.Heart
    ),
)

val dropletButton = listOf(
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
        icon = R.drawable.icon_heart,
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
        icon = R.drawable.outline_home,
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
            animationSpec = spring(
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
    )
)