package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tico.pomorodo.ui.home.view.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = BottomNavigationDestination.Timer.name,
    navigateToConcentrationMode: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToModifyProfile: () -> Unit,
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        timerScreen(setState = setTimerState, navigate = navigateToConcentrationMode)
        todoScreen(
            navigateToCategory = navigateToCategory,
            navigateToAddCategory = navigateToAddCategory,
            navigateToHistory = navigateToHistory
        )
        myInfoScreen(navigateToModifyProfile = navigateToModifyProfile)
    }
}
