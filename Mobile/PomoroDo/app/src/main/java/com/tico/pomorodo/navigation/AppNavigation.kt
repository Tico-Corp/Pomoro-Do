package com.tico.pomorodo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tico.pomorodo.ui.MyInfoScreen
import com.tico.pomorodo.ui.TodoScreen
import com.tico.pomorodo.ui.timer.view.TimerRootScreen

const val TIMER_ROUTE = "timer_route"
const val TODO_ROUTE = "todo_route"
const val MY_INFO_ROUTE = "my_info_route"

fun NavController.navigateToTimer(navOptions: NavOptions) = navigate(TIMER_ROUTE, navOptions)
fun NavController.navigateToTodo(navOptions: NavOptions) = navigate(TODO_ROUTE, navOptions)
fun NavController.navigateToMyInfo(navOptions: NavOptions) = navigate(MY_INFO_ROUTE, navOptions)

fun NavGraphBuilder.timerScreen() {
    composable(
        route = TIMER_ROUTE
    ) {
        TimerRootScreen()
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