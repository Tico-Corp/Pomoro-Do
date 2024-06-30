package com.tico.pomorodo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tico.pomorodo.ui.auth.view.LogInScreen
import com.tico.pomorodo.ui.auth.view.SignUpScreen
import com.tico.pomorodo.ui.home.view.HomeScreen
import com.tico.pomorodo.ui.home.view.MyInfoScreen
import com.tico.pomorodo.ui.home.view.TodoScreen
import com.tico.pomorodo.ui.splash.view.SplashScreen
import com.tico.pomorodo.ui.timer.view.ConcentrationTimerScreen
import com.tico.pomorodo.ui.timer.view.TimerRootScreen

// home navigation - navigate
fun NavController.navigateToTimer(navOptions: NavOptions) =
    navigate(HomeNavigationDestination.Timer.name, navOptions)

fun NavController.navigateToTodo(navOptions: NavOptions) =
    navigate(HomeNavigationDestination.Todo.name, navOptions)

fun NavController.navigateToMyInfo(navOptions: NavOptions) =
    navigate(HomeNavigationDestination.MyPage.name, navOptions)

// main navigation - navigate
fun NavController.navigateToLogIn() = navigate(MainNavigationDestination.LogIn.name)
fun NavController.navigateToSignUp() = navigate(MainNavigationDestination.SignUp.name)
fun NavController.navigateToHome() = navigate(MainNavigationDestination.Home.name)
fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.ConcentrationMode.name)


// home navigation - composable route
fun NavGraphBuilder.timerScreen(navigate: () -> Unit) {
    composable(route = HomeNavigationDestination.Timer.name) {
        TimerRootScreen(navigate = navigate)
    }
}

fun NavGraphBuilder.todoScreen() {
    composable(route = HomeNavigationDestination.Todo.name) {
        TodoScreen()
    }
}

fun NavGraphBuilder.myInfoScreen() {
    composable(route = HomeNavigationDestination.MyPage.name) {
        MyInfoScreen()
    }
}

// home navigation - composable route
fun NavGraphBuilder.splashScreen(navigate: () -> Unit) {
    composable(route = MainNavigationDestination.Splash.name) {
        SplashScreen(navigate = navigate)
    }
}

fun NavGraphBuilder.logInScreen(navigate: () -> Unit) {
    composable(route = MainNavigationDestination.LogIn.name) {
        LogInScreen(navigate = navigate)
    }
}

fun NavGraphBuilder.signUpScreen(navigate: () -> Unit) {
    composable(route = MainNavigationDestination.SignUp.name) {
        SignUpScreen(navigate = navigate)
    }
}

fun NavGraphBuilder.homeScreen(navigateToConcentrationMode: () -> Unit) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(navigateToConcentrationMode = navigateToConcentrationMode)
    }
}

fun NavGraphBuilder.concentrationModeScreen() {
    composable(route = MainNavigationDestination.ConcentrationMode.name) {
        ConcentrationTimerScreen()
    }
}