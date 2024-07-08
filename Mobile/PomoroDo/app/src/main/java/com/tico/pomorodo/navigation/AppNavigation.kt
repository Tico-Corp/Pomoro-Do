package com.tico.pomorodo.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tico.pomorodo.ui.auth.view.LogInScreen
import com.tico.pomorodo.ui.auth.view.SignUpRoute
import com.tico.pomorodo.ui.category.view.AddCategoryScreenRoute
import com.tico.pomorodo.ui.category.view.CategoryScreenRoute
import com.tico.pomorodo.ui.category.view.GroupMemberChooseScreenRoute
import com.tico.pomorodo.ui.category.view.InfoCategoryScreenRoute
import com.tico.pomorodo.ui.history.view.HistoryRoute
import com.tico.pomorodo.ui.home.view.HomeScreen
import com.tico.pomorodo.ui.member.view.MyPageScreen
import com.tico.pomorodo.ui.splash.view.SplashScreen
import com.tico.pomorodo.ui.timer.running.view.BreakTimerScreen
import com.tico.pomorodo.ui.timer.running.view.ConcentrationTimerScreen
import com.tico.pomorodo.ui.timer.setup.view.TimerRootScreen
import com.tico.pomorodo.ui.todo.view.TodoScreenRoute

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

fun NavController.navigateToCategory() = navigate(MainNavigationDestination.Category.name)
fun NavController.navigateToAddCategory() = navigate(MainNavigationDestination.AddCategory.name)
fun NavController.navigateToInfoCategory() = navigate(MainNavigationDestination.InfoCategory.name)
fun NavController.navigateToGroupMemberChoose() =
    navigate(MainNavigationDestination.GroupMemberChoose.name)

fun NavController.navigateToHistory() = navigate(MainNavigationDestination.History.name)


// home navigation - composable route
fun NavGraphBuilder.timerScreen(
    setState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigate: () -> Unit
) {
    composable(route = BottomNavigationDestination.Timer.name) {
        TimerRootScreen(setState = setState, navigate = navigate)
    }
}

fun NavGraphBuilder.todoScreen(
    navigateToAddCategory: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToHistory: () -> Unit
) {
    composable(route = BottomNavigationDestination.Todo.name) {
        TodoScreenRoute(
            navigateToAddCategory = navigateToAddCategory,
            navigateToCategory = navigateToCategory,
            navigateToHistory = navigateToHistory
        )
    }
}

fun NavGraphBuilder.myInfoScreen() {
    composable(route = BottomNavigationDestination.MyInfo.name) {
        MyPageScreen()
    }
}

// main navigation - composable route
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

fun NavGraphBuilder.signUpScreen(navController: NavHostController, navigate: () -> Unit) {
    composable(route = MainNavigationDestination.SignUp.name) { navBackStackEntry ->
        val authNavBackStackEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(MainNavigationDestination.LogIn.name)
        }
        SignUpRoute(
            navBackStackEntry = authNavBackStackEntry,
            navigate = navigate
        )
    }
}

fun NavGraphBuilder.homeScreen(
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigateToConcentrationMode: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(
            setTimerState = setTimerState,
            navigateToConcentrationMode = navigateToConcentrationMode,
            navigateToCategory = navigateToCategory,
            navigateToAddCategory = navigateToAddCategory,
            navigateToHistory = navigateToHistory
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

fun NavGraphBuilder.categoryScreen(
    navigateToAddCategory: () -> Unit,
    navigateToInfoCategory: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.Category.name) {
        CategoryScreenRoute(
            navigateToAddCategory = navigateToAddCategory,
            navigateToBack = navigateToBack,
            navigateToInfoCategory = navigateToInfoCategory
        )
    }
}

fun NavGraphBuilder.addCategoryScreen(
    navController: NavController,
    navigateToCategory: () -> Unit,
    navigateToGroupMemberChoose: () -> Unit,
    navigateToBack: () -> Unit,
) {
    composable(route = MainNavigationDestination.AddCategory.name) { backStackEntry ->
        val navBackStackEntry = remember(backStackEntry) {
            try {
                navController.getBackStackEntry(MainNavigationDestination.Category.name)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
        AddCategoryScreenRoute(
            navigateToCategory = navigateToCategory,
            navigateToBack = navigateToBack,
            navigateToGroupMemberChoose = navigateToGroupMemberChoose,
            navBackStackEntry = navBackStackEntry
        )
    }
}

fun NavGraphBuilder.infoCategoryScreen(
    navigateToCategory: () -> Unit,
    navigateToGroupMemberChoose: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.InfoCategory.name) {
        InfoCategoryScreenRoute(
            navigateToCategory = navigateToCategory,
            navigateToBack = navigateToBack,
            navigateToGroupMemberChoose = navigateToGroupMemberChoose
        )
    }
}

fun NavGraphBuilder.historyScreen(
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.History.name) {
        HistoryRoute(navigateToBack = navigateToBack)
    }
}

fun NavGraphBuilder.groupMemberChooseScreen(
    navController: NavController,
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.GroupMemberChoose.name) { backStackEntry ->
        val navBackStackEntry = remember(backStackEntry) {
            try {
                navController.getBackStackEntry(MainNavigationDestination.Category.name)
            } catch (e: IllegalArgumentException) {
                navController.getBackStackEntry(MainNavigationDestination.AddCategory.name)
            }
        }
        GroupMemberChooseScreenRoute(
            navBackStackEntry = navBackStackEntry,
            navigateToBack = navigateToBack
        )
    }
}