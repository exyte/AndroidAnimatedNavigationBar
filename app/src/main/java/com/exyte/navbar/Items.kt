package com.exyte.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import com.exyte.navbar.navbar.items.colorButtons.AnimationType
import com.exyte.navbar.navbar.items.colorButtons.BellAnimation
import com.exyte.navbar.navbar.items.colorButtons.ButtonBackground

data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: AnimationType = BellAnimation(tween(500), background = ButtonBackground()),
)