package com.exyte.navbar.navbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.noRippleClickable(
    onClick: () -> Unit
) = this.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() } // This is mandatory
) {
    onClick()
}