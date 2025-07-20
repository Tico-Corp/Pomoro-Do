package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tico.pomorodo.ui.home.view.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = BottomNavigationDestination.TIMER.name,
    navigateToConcentrationMode: () -> Unit,
    navigateToBreakMode: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToModifyProfile: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToAddFollowerScreen: () -> Unit,
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit,
) {
    val navController = appState.homeNavController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        timerScreen(
            setState = setTimerState,
            navigateToConcentrationMode = navigateToConcentrationMode,
            navigateToBreakMode = navigateToBreakMode
        )
        todoScreen(
            navigateToCategory = navigateToCategory,
            navigateToAddCategory = navigateToAddCategory,
            navigateToHistory = navigateToHistory
        )
        followScreen(navigateToAddFollowerScreen = navigateToAddFollowerScreen)
        myInfoScreen(
            navigateToModifyProfile = navigateToModifyProfile,
            navigateToSettingScreen = navigateToSettingScreen
        )
    }
}
