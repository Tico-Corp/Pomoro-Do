package com.tico.pomorodo.ui.home.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.MY_INFO_ROUTE
import com.tico.pomorodo.navigation.TIMER_ROUTE
import com.tico.pomorodo.navigation.TODO_ROUTE
import com.tico.pomorodo.navigation.navigateToMyInfo
import com.tico.pomorodo.navigation.navigateToTimer
import com.tico.pomorodo.navigation.navigateToTodo

class AppState(val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: BottomNavigationDestination?
        @Composable
        get() = when (currentDestination?.route) {
            TIMER_ROUTE -> BottomNavigationDestination.TIMER
            TODO_ROUTE -> BottomNavigationDestination.TODO
            MY_INFO_ROUTE -> BottomNavigationDestination.MY_INFO
            else -> null
        }

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
            BottomNavigationDestination.TIMER -> navController.navigateToTimer(navOptions)
            BottomNavigationDestination.TODO -> navController.navigateToTodo(navOptions)
            BottomNavigationDestination.MY_INFO -> navController.navigateToMyInfo(navOptions)
        }
    }
}