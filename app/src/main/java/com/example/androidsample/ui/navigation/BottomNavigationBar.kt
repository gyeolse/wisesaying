package com.example.androidsample.ui.navigation

import SearchScreen
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidsample.ui.screens.FavoriteScreen
import com.example.androidsample.ui.screens.HomeScreen
import com.example.androidsample.ui.screens.SettingScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Log.d("Navigation", "Current destination: ${currentDestination?.route}")

//    // BackPressHandler를 최상위에 추가
//    BackPressHandler(
//        onBackPressed = { Log.d("MainScreen", "Back pressed") }
//    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                BottomNavigationItem().bottomNavigationItems().forEach { navigationItem ->
                    NavigationBarItem(
                        selected = when (navigationItem.route) {
                            ScreenInfo.Home.route -> currentDestination?.route?.startsWith(ScreenInfo.Home.route) == true
                            else -> navigationItem.route == currentDestination?.route
                        },
                        label = { null },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color.White
                        ),
                        icon = {
                            Icon(
                                imageVector = navigationItem.icon,
                                contentDescription = navigationItem.label,
                                tint = if (currentDestination?.route?.startsWith(navigationItem.route) == true) Color.Black else Color.Gray
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenInfo.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(ScreenInfo.Home.route) {
                HomeScreen(navController, selectedUid = null)
            }
            composable(
                route = "${ScreenInfo.Home.route}/{uid}",
                arguments = listOf(navArgument("uid") { type = NavType.IntType })
            ) { backStackEntry ->
                val uid = backStackEntry.arguments?.getInt("uid")
                HomeScreen(navController, selectedUid = uid)
            }
            composable(ScreenInfo.Search.route) {
                SearchScreen(navController)
            }
            composable(ScreenInfo.Favorite.route) {
                FavoriteScreen(navController)
            }
            composable(ScreenInfo.Settings.route) {
                SettingScreen(navController)
            }
        }
    }
}