package com.exyte.navbar.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun NavBarTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = lightColors(),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}