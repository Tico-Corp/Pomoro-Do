package com.tico.pomorodo.ui.home.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.navigation.TIMER_ROUTE
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Composable
fun HomeScreen(navController: NavController, timerViewModel: TimerViewModel) {
    val homeNavController = rememberNavController()
    val appState = AppState(homeNavController)

    Scaffold(
        bottomBar = { BottomBar(appState) },
        containerColor = PomoroDoTheme.colorScheme.background
    ) { innerPadding ->
        AppNavHost(
            appState = appState,
            parentNavController = navController,
            Modifier.padding(innerPadding),
            startDestination = TIMER_ROUTE,
            timerViewModel = timerViewModel
        )
    }
}