package com.tico.pomorodo.ui.home.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.navigateToMyInfo
import com.tico.pomorodo.navigation.navigateToTimer
import com.tico.pomorodo.navigation.navigateToTodo

class AppState(val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController
            .currentBackStackEntryAsState().value?.destination

    val bottomNavigationDestinationList = BottomNavigationDestination.entries

    fun navigateToDestination(topLevelDestination: BottomNavigationDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            BottomNavigationDestination.Timer -> navController.navigateToTimer(navOptions)
            BottomNavigationDestination.Todo -> navController.navigateToTodo(navOptions)
            BottomNavigationDestination.MyInfo -> navController.navigateToMyInfo(navOptions)
        }
    }
}