package com.tico.pomorodo.ui.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tico.pomorodo.MainViewModel
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.navigateToFollow
import com.tico.pomorodo.navigation.navigateToMyInfo
import com.tico.pomorodo.navigation.navigateToTimer
import com.tico.pomorodo.navigation.navigateToTodo

@Composable
fun rememberAppState(
    mainViewModel: MainViewModel = hiltViewModel(),
    mainNavController: NavHostController = rememberNavController(),
    homeNavController: NavHostController = rememberNavController(),
): AppState {
    val isNetworkConnected: State<Boolean> =
        mainViewModel.isNetworkConnected
            .collectAsState(initial = false)
    val isOffline = remember { mutableStateOf(false) }

    return remember(mainNavController, homeNavController, isNetworkConnected, isOffline.value) {
        AppState(
            mainNavController = mainNavController,
            homeNavController = homeNavController,
            isNetworkConnected = isNetworkConnected,
            isOffline = isOffline
        )
    }
}

@Stable
data class AppState(
    val mainNavController: NavHostController,
    val homeNavController: NavHostController,
    val isNetworkConnected: State<Boolean>,
    val isOffline: MutableState<Boolean>
) {
    val currentDestination: NavDestination?
        @Composable
        get() = homeNavController
            .currentBackStackEntryAsState().value?.destination

    val bottomNavigationDestinationList = BottomNavigationDestination.entries

    fun setIsOffline(flag: Boolean) {
        isOffline.value = flag
    }

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