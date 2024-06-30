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
    timerViewModel: TimerViewModel,
    navigate: () -> Unit
) {
    composable(
        route = TIMER_ROUTE
    ) {
        TimerRootScreen(
            navigate = navigate,
            timerViewModel = timerViewModel
        )
    }
}

fun NavGraphBuilder.todoScreen() {
    composable(
        route = TODO_ROUTE
    ) {
        TodoScreen()
    }
}

fun NavGraphBuilder.myInfoScreen() {
    composable(
        route = MY_INFO_ROUTE
    ) {
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

fun NavGraphBuilder.homeScreen(
    timerViewModel: TimerViewModel,
    navigateToConcentrationMode: () -> Unit,
) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(
            navigateToConcentrationMode = navigateToConcentrationMode,
            timerViewModel = timerViewModel
        )
    }
}

fun NavGraphBuilder.concentrationModeScreen(timerViewModel: TimerViewModel) {
    composable(route = MainNavigationDestination.ConcentrationMode.name) {
        ConcentrationTimerScreen(timerViewModel = timerViewModel)
    }
}