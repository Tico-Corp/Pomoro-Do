package com.tico.pomorodo.ui.home.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun HomeScreen(
    navigateToConcentrationMode: () -> Unit,
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit
) {
    val homeNavController = rememberNavController()
    val appState = AppState(homeNavController)

    Scaffold(
        bottomBar = { BottomBar(appState) },
        containerColor = PomoroDoTheme.colorScheme.background
    ) { innerPadding ->
        AppNavHost(
            appState = appState,
            Modifier.padding(innerPadding),
            startDestination = BottomNavigationDestination.Timer.name,
            navigateToConcentrationMode = navigateToConcentrationMode,
            setTimerState = setTimerState
        )
    }
}