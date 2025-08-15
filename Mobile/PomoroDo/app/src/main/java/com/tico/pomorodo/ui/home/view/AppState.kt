package com.tico.pomorodo.ui.home.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tico.pomorodo.MainViewModel
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.navigation.navigateToFollow
import com.tico.pomorodo.navigation.navigateToMyInfo
import com.tico.pomorodo.navigation.navigateToTimer
import com.tico.pomorodo.navigation.navigateToTodo

@Composable
fun rememberAppState(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
): AppState {
    val isNetworkConnected: State<Boolean> =
        mainViewModel.isNetworkConnected
            .collectAsState(initial = false)
    val isOffline = remember { mutableStateOf(false) }

    return remember(navController, isNetworkConnected, isOffline.value) {
        AppState(
            navController = navController,
            isNetworkConnected = isNetworkConnected,
            isOffline = isOffline,
            snackBarHostState = snackBarHostState
        )
    }
}

@Stable
data class AppState(
    val navController: NavHostController,
    val snackBarHostState: SnackbarHostState,
    val isNetworkConnected: State<Boolean>,
    val isOffline: MutableState<Boolean>
) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController
            .currentBackStackEntryAsState().value?.destination

    val bottomNavigationDestinationList = BottomNavigationDestination.entries

    fun setIsOffline(flag: Boolean) {
        isOffline.value = flag
    }

    fun navigateToDestination(topLevelDestination: BottomNavigationDestination) {
        val navOptions = navOptions {
            popUpTo(MainNavigationDestination.HOME.name) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            BottomNavigationDestination.TIMER -> navController.navigateToTimer(navOptions)
            BottomNavigationDestination.TODO -> navController.navigateToTodo(navOptions)
            BottomNavigationDestination.FOLLOW -> navController.navigateToFollow(navOptions)
            BottomNavigationDestination.MY_INFO -> navController.navigateToMyInfo(navOptions)
        }
    }
}