package com.seyefactory.wisespoon.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = ScreenInfo.Home.route,

            ),
            BottomNavigationItem(
                label = "Search",
                icon = Icons.Rounded.Search,
                route = ScreenInfo.Search.route
            ),
            BottomNavigationItem(
                label = "Favorite",
                icon = Icons.Filled.Favorite,
                route = ScreenInfo.Favorite.route
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = ScreenInfo.Settings.route
            ),
        )
    }
}