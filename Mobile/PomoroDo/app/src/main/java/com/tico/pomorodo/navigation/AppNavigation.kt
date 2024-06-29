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
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

const val TIMER_ROUTE = "timer_route"
const val TODO_ROUTE = "todo_route"
const val MY_INFO_ROUTE = "my_info_route"

// home navigation - navigate
fun NavController.navigateToTimer(navOptions: NavOptions) = navigate(TIMER_ROUTE, navOptions)
fun NavController.navigateToTodo(navOptions: NavOptions) = navigate(TODO_ROUTE, navOptions)
fun NavController.navigateToMyInfo(navOptions: NavOptions) = navigate(MY_INFO_ROUTE, navOptions)

// main navigation - navigate
fun NavController.navigateToLogIn() = navigate(MainNavigationDestination.LogIn.name)
fun NavController.navigateToSignUp() = navigate(MainNavigationDestination.SignUp.name)
fun NavController.navigateToHome() = navigate(MainNavigationDestination.Home.name)
fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.ConcentrationMode.name)


// home navigation - composable route
fun NavGraphBuilder.timerScreen(
    parentNavController: NavController,
    timerViewModel: TimerViewModel
) {
    composable(
        route = TIMER_ROUTE
    ) {
        TimerRootScreen(parentNavController = parentNavController, timerViewModel = timerViewModel)
    }
}

fun NavGraphBuilder.todoScreen(parentNavController: NavController) {
    composable(
        route = TODO_ROUTE
    ) {
        TodoScreen(parentNavController)
    }
}

fun NavGraphBuilder.myInfoScreen(parentNavController: NavController) {
    composable(
        route = MY_INFO_ROUTE
    ) {
        MyInfoScreen(parentNavController)
    }
}

// home navigation - composable route
fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(route = MainNavigationDestination.Splash.name) {
        SplashScreen(navController = navController)
    }
}

fun NavGraphBuilder.logInScreen(navController: NavController) {
    composable(route = MainNavigationDestination.LogIn.name) {
        LogInScreen(navController = navController)
    }
}

fun NavGraphBuilder.signUpScreen(navController: NavController) {
    composable(route = MainNavigationDestination.SignUp.name) {
        SignUpScreen(navController = navController)
    }
}

fun NavGraphBuilder.homeScreen(navController: NavController, timerViewModel: TimerViewModel) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(navController = navController, timerViewModel = timerViewModel)
    }
}

fun NavGraphBuilder.concentrationModeScreen(
    navController: NavController,
    timerViewModel: TimerViewModel
) {
    composable(route = MainNavigationDestination.ConcentrationMode.name) {
        ConcentrationTimerScreen(
            navController = navController,
            timerViewModel = timerViewModel
        )
    }
}