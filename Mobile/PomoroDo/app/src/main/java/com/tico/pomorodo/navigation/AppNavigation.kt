package com.tico.pomorodo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.ui.AppState
import com.tico.pomorodo.ui.MyInfoScreen
import com.tico.pomorodo.ui.TodoScreen
import com.tico.pomorodo.ui.common.view.BottomBar
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import com.tico.pomorodo.ui.timer.view.ConcentrationTimerScreen
import com.tico.pomorodo.ui.timer.view.TimerRootScreen
import com.tico.pomorodo.ui.timer.viewmodel.TimerViewModel

const val TIMER_ROUTE = "timer_route"
const val TODO_ROUTE = "todo_route"
const val MY_INFO_ROUTE = "my_info_route"

fun NavController.navigateToTimer(navOptions: NavOptions) = navigate(TIMER_ROUTE, navOptions)
fun NavController.navigateToTodo(navOptions: NavOptions) = navigate(TODO_ROUTE, navOptions)
fun NavController.navigateToMyInfo(navOptions: NavOptions) = navigate(MY_INFO_ROUTE, navOptions)

fun NavController.navigateToHome() = navigate(MainNavigationDestination.Home.name)

fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.ConcentrationMode.name)

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

fun NavGraphBuilder.homeScreen(mainNavController: NavController, timerViewModel: TimerViewModel) {
    composable(route = MainNavigationDestination.Home.name) {
        val homeNavController = rememberNavController()
        val appState = AppState(homeNavController)

        Scaffold(
            bottomBar = { BottomBar(appState) },
            containerColor = PomoroDoTheme.colorScheme.background
        ) { innerPadding ->
            AppNavHost(
                appState = appState,
                parentNavController = mainNavController,
                Modifier.padding(innerPadding),
                startDestination = TIMER_ROUTE,
                timerViewModel = timerViewModel
            )
        }
    }
}

fun NavGraphBuilder.concentrationModeScreen(
    mainNavController: NavController,
    timerViewModel: TimerViewModel
) {
    composable(route = MainNavigationDestination.ConcentrationMode.name) {
        ConcentrationTimerScreen(
            mainNavController = mainNavController,
            timerViewModel = timerViewModel
        )
    }
}