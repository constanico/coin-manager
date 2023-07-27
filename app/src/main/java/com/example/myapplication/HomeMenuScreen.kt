package com.example.myapplication

sealed class HomeMenuScreen(
    val route: String,
    val title: String
) {
    object Add : HomeMenuScreen(
        route = "ADD",
        title = "ADD"
    )

    object Edit : HomeMenuScreen(
        route = "EDIT",
        title = "EDIT"
    )
}