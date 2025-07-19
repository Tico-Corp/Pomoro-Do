package com.tico.pomorodo.ui.home.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.ui.common.view.BackOnPressed
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun HomeScreen(
    appState: AppState,
    navigateToConcentrationMode: () -> Unit,
    navigateToBreakMode: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToModifyProfile: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    navigateToAddFollowerScreen: () -> Unit,
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit
) {
    BackOnPressed()
    Scaffold(
        bottomBar = { BottomBar(appState) },
        containerColor = PomoroDoTheme.colorScheme.background
    ) { innerPadding ->
        AppNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding),
            startDestination = BottomNavigationDestination.TIMER.name,
            navigateToConcentrationMode = navigateToConcentrationMode,
            navigateToBreakMode = navigateToBreakMode,
            navigateToCategory = navigateToCategory,
            navigateToAddCategory = navigateToAddCategory,
            navigateToHistory = navigateToHistory,
            navigateToModifyProfile = navigateToModifyProfile,
            navigateToSettingScreen = navigateToSettingScreen,
            navigateToAddFollowerScreen = navigateToAddFollowerScreen,
            setTimerState = setTimerState,
        )
    }
}