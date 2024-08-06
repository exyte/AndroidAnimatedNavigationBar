package com.exyte.navbar.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.exyte.navbar.ui.theme.ElectricViolet
import com.exyte.navbar.ui.theme.HotRed
import com.exyte.navbar.ui.theme.NavyBlue


@Composable
fun MessagesScreen() {
    Column (
        modifier = Modifier.fillMaxSize().background(NavyBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Messages", fontSize = 50.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}