package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tico.pomorodo.ui.home.view.AppState
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = TIMER_ROUTE,
    timerViewModel: TimerViewModel,
    navigateToConcentrationMode: () -> Unit
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        timerScreen(timerViewModel = timerViewModel, navigate = navigateToConcentrationMode)
        todoScreen()
        myInfoScreen()
    }
}
