package com.tico.pomorodo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.navigation.concentrationModeScreen
import com.tico.pomorodo.navigation.homeScreen
import com.tico.pomorodo.navigation.logInScreen
import com.tico.pomorodo.navigation.signUpScreen
import com.tico.pomorodo.navigation.splashScreen
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PomoroDoTheme.colorScheme.background
    ) {
        val mainNavController = rememberNavController()
        val timerViewModel: TimerViewModel = hiltViewModel()

        Scaffold { innerPadding ->
            NavHost(
                navController = mainNavController,
                startDestination = MainNavigationDestination.Splash.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                splashScreen(navController = mainNavController)
                logInScreen(navController = mainNavController)
                signUpScreen(navController = mainNavController)
                homeScreen(
                    navController = mainNavController,
                    timerViewModel = timerViewModel
                )
                concentrationModeScreen(
                    navController = mainNavController,
                    timerViewModel = timerViewModel
                )
            }
        }
    }
}