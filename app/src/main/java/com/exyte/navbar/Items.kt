package com.exyte.navbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
)