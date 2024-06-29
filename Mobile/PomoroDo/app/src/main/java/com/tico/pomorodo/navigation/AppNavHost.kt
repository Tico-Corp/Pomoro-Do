package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.tico.pomorodo.ui.home.view.AppState
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Composable
fun AppNavHost(
    appState: AppState,
    parentNavController: NavController,
    modifier: Modifier = Modifier,
    startDestination: String = TIMER_ROUTE,
    timerViewModel: TimerViewModel
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        timerScreen(parentNavController = parentNavController, timerViewModel = timerViewModel)
        todoScreen(parentNavController)
        myInfoScreen(parentNavController)
    }
}
