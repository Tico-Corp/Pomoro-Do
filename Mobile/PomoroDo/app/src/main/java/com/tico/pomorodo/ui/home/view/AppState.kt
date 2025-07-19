package com.tico.pomorodo.ui.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.navigateToFollow
import com.tico.pomorodo.navigation.navigateToMyInfo
import com.tico.pomorodo.navigation.navigateToTimer
import com.tico.pomorodo.navigation.navigateToTodo

@Composable
fun rememberAppState(
    mainNavController: NavHostController = rememberNavController(),
    homeNavController: NavHostController = rememberNavController(),
): AppState {

    return remember(mainNavController, homeNavController) {
        AppState(
            mainNavController = mainNavController,
            homeNavController = homeNavController,
        )
    }
}

@Stable
data class AppState(
    val mainNavController: NavHostController,
    val homeNavController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable
        get() = homeNavController
            .currentBackStackEntryAsState().value?.destination

    val bottomNavigationDestinationList = BottomNavigationDestination.entries

    fun navigateToDestination(topLevelDestination: BottomNavigationDestination) {
        val navOptions = navOptions {
            popUpTo(homeNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            BottomNavigationDestination.TIMER -> homeNavController.navigateToTimer(navOptions)
            BottomNavigationDestination.TODO -> homeNavController.navigateToTodo(navOptions)
            BottomNavigationDestination.FOLLOW -> homeNavController.navigateToFollow(navOptions)
            BottomNavigationDestination.MY_INFO -> homeNavController.navigateToMyInfo(navOptions)
        }
    }
}