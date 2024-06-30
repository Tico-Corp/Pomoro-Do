package com.tico.pomorodo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.navigation.concentrationModeScreen
import com.tico.pomorodo.navigation.homeScreen
import com.tico.pomorodo.navigation.logInScreen
import com.tico.pomorodo.navigation.navigateToConcentrationMode
import com.tico.pomorodo.navigation.navigateToHome
import com.tico.pomorodo.navigation.navigateToLogIn
import com.tico.pomorodo.navigation.navigateToSignUp
import com.tico.pomorodo.navigation.signUpScreen
import com.tico.pomorodo.navigation.splashScreen
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PomoroDoTheme.colorScheme.background
    ) {
        val mainNavController = rememberNavController()

        Scaffold { innerPadding ->
            NavHost(
                navController = mainNavController,
                startDestination = MainNavigationDestination.Splash.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                splashScreen(navigate = mainNavController::navigateToLogIn)
                logInScreen(navigate = mainNavController::navigateToSignUp)
                signUpScreen(navigate = mainNavController::navigateToHome)
                homeScreen(navigateToConcentrationMode = mainNavController::navigateToConcentrationMode)
                concentrationModeScreen()
            }
        }
    }
}