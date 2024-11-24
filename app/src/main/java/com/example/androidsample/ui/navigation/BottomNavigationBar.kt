package com.example.androidsample.ui.navigation

import SearchScreen
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.androidsample.ui.screens.ProfileScreen
import com.example.androidsample.ui.screens.SettingScreen
import com.example.androidsample.ui.screens.TodoScreen
import com.example.androidsample.ui.viewmodel.TodoViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    // initialize the default selected item.
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Log.d("Navigation", "Current destination: ${currentDestination?.route}")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White, // 네비게이션 바 배경 (필요시 변경)
                tonalElevation = 0.dp // Elevation 제거
            ) {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = when (navigationItem.route) {
                            ScreenInfo.Home.route -> currentDestination?.route?.startsWith(ScreenInfo.Home.route) == true
                            else -> navigationItem.route == currentDestination?.route
                        },
//                        selected = navigationItem.route == navController.currentBackStackEntryAsState().value?.destination?.route,
//                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                                null
//                            Text(navigationItem.label)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black, // 선택된 아이콘 색상
                            unselectedIconColor = Color.Gray, // 선택되지 않은 아이콘 색상
                            indicatorColor = Color.White // 배경 타원 제거
                        ),
                        icon = {
                            // 아이콘의 색상을 `selected` 상태에 따라 동적으로 설정
                            Icon(
                                imageVector = navigationItem.icon,
                                contentDescription = navigationItem.label,
                                tint = if (currentDestination?.route == navigationItem.route) Color.Black else Color.Gray
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
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(ScreenInfo.Home.route) { HomeScreen(navController) }
            composable("${ScreenInfo.Home.route}/{uid}", arguments = listOf(navArgument("uid") { type = NavType.IntType })) { backStackEntry ->
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