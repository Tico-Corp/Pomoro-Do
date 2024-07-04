package com.tico.pomorodo.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tico.pomorodo.ui.auth.view.LogInScreen
import com.tico.pomorodo.ui.auth.view.SignUpScreen
import com.tico.pomorodo.ui.home.view.HomeScreen
import com.tico.pomorodo.ui.home.view.TodoScreen
import com.tico.pomorodo.ui.member.view.MyPageScreen
import com.tico.pomorodo.ui.splash.view.SplashScreen
import com.tico.pomorodo.ui.timer.running.view.BreakTimerScreen
import com.tico.pomorodo.ui.timer.running.view.ConcentrationTimerScreen
import com.tico.pomorodo.ui.timer.setup.view.TimerRootScreen

// home navigation - navigate
fun NavController.navigateToTimer(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.Timer.name, navOptions)

fun NavController.navigateToTodo(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.Todo.name, navOptions)

fun NavController.navigateToMyInfo(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.MyInfo.name, navOptions)

// main navigation - navigate
fun NavController.navigateToLogIn() = navigate(MainNavigationDestination.LogIn.name)
fun NavController.navigateToSignUp() = navigate(MainNavigationDestination.SignUp.name)
fun NavController.navigateToHome() = navigate(MainNavigationDestination.Home.name)
fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.ConcentrationMode.name)

fun NavController.navigateToBreakMode() =
    navigate(MainNavigationDestination.BreakMode.name)


// home navigation - composable route
fun NavGraphBuilder.timerScreen(
    setState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigate: () -> Unit
) {
    composable(route = BottomNavigationDestination.Timer.name) {
        TimerRootScreen(setState = setState, navigate = navigate)
    }
}

fun NavGraphBuilder.todoScreen() {
    composable(route = BottomNavigationDestination.Todo.name) {
        TodoScreen()
    }
}

fun NavGraphBuilder.myInfoScreen() {
    composable(route = BottomNavigationDestination.MyInfo.name) {
        MyPageScreen()
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
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigateToConcentrationMode: () -> Unit
) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(
            setTimerState = setTimerState,
            navigateToConcentrationMode = navigateToConcentrationMode
        )
    }
}

fun NavGraphBuilder.concentrationModeScreen(
    getState: (String) -> Int?,
    navigateToBreakMode: () -> Unit
) {
    composable(route = MainNavigationDestination.ConcentrationMode.name) {
        ConcentrationTimerScreen(getState = getState, navigate = navigateToBreakMode)
    }
}

fun NavGraphBuilder.breakModeScreen(navController: NavController) {
    composable(route = MainNavigationDestination.BreakMode.name) { backStackEntry ->
        val navBackStackEntry = remember(backStackEntry) {
            navController.getBackStackEntry(MainNavigationDestination.ConcentrationMode.name)
        }
        BreakTimerScreen(
            navBackStackEntry = navBackStackEntry,
            navigate = {
                navController.popBackStack(
                    MainNavigationDestination.Home.name,
                    inclusive = false
                )
            }
        )
    }
}