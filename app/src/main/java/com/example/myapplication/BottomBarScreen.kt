package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "HOME",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        icon = Icons.Default.Person
    )
}