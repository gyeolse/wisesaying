package com.example.androidsample.ui.navigation

sealed class ScreenInfo(val route: String) {
    object Home: ScreenInfo("home_screen")
    object Profile: ScreenInfo("profile_screen")
    object Settings: ScreenInfo("setting_screen")
    object Todo: ScreenInfo("todo_screen")

    object Favorite: ScreenInfo("favorite_screen")
}