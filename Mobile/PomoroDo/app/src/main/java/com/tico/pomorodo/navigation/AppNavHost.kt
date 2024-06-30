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
    navigateToConcentrationMode: () -> Unit
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        timerScreen(navigate = navigateToConcentrationMode)
        todoScreen()
        myInfoScreen()
    }
}
