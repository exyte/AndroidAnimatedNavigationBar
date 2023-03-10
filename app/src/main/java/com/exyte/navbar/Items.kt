package com.exyte.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import com.exyte.navbar.navbar.items.colorButtons.ColorButtonAnimation
import com.exyte.navbar.navbar.items.colorButtons.BellColorButton
import com.exyte.navbar.navbar.items.colorButtons.ButtonBackground

data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: ColorButtonAnimation = BellColorButton(
        tween(500),
        background = ButtonBackground(R.drawable.icon_plus)
    ),
)