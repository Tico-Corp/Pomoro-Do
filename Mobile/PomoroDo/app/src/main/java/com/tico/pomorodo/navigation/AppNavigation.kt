package com.tico.pomorodo.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tico.pomorodo.ui.auth.view.LogInRoute
import com.tico.pomorodo.ui.auth.view.SignUpRoute
import com.tico.pomorodo.ui.category.view.CategoryAddScreenRoute
import com.tico.pomorodo.ui.category.view.CategoryInfoScreenRoute
import com.tico.pomorodo.ui.category.view.CategoryScreenRoute
import com.tico.pomorodo.ui.category.view.GroupMemberChooseRoute
import com.tico.pomorodo.ui.common.view.BackOnPressed
import com.tico.pomorodo.ui.follow.view.AddFollowerScreen
import com.tico.pomorodo.ui.follow.view.FollowListScreen
import com.tico.pomorodo.ui.history.view.HistoryRoute
import com.tico.pomorodo.ui.member.view.MyPageScreen
import com.tico.pomorodo.ui.setting.view.AppThemeScreen
import com.tico.pomorodo.ui.setting.view.ModifyProfileScreen
import com.tico.pomorodo.ui.setting.view.SettingScreen
import com.tico.pomorodo.ui.splash.view.SplashScreen
import com.tico.pomorodo.ui.timer.running.view.BreakTimerScreen
import com.tico.pomorodo.ui.timer.running.view.ConcentrationTimerScreen
import com.tico.pomorodo.ui.timer.setup.view.TimerRootScreen
import com.tico.pomorodo.ui.todo.view.FollowTodoScreenRoute
import com.tico.pomorodo.ui.todo.view.TodoScreenRoute

// home navigation - navigate
fun NavController.navigateToTimer(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.TIMER.name, navOptions)

fun NavController.navigateToTodo(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.TODO.name, navOptions)

fun NavController.navigateToFollow(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.FOLLOW.name, navOptions)

fun NavController.navigateToMyInfo(navOptions: NavOptions) =
    navigate(BottomNavigationDestination.MY_INFO.name, navOptions)

// main navigation - navigate
fun NavController.navigateToLogIn() = navigate(MainNavigationDestination.LOG_IN.name) {
    popUpTo(MainNavigationDestination.SPLASH.name) { inclusive = true }
}

fun NavController.navigateToSignUp() = navigate(MainNavigationDestination.SIGN_UP.name)

fun NavController.navigateToHome() {
    navigate(MainNavigationDestination.HOME.name) {
        popUpTo(graph.id) { inclusive = true }
    }
}

fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.CONCENTRATION_MODE.name)

fun NavController.navigateToBreakMode() =
    navigate(MainNavigationDestination.BREAK_MODE.name)

fun NavController.navigateToCategory() = navigate(MainNavigationDestination.CATEGORY.name)
fun NavController.navigateToAddCategory() = navigate(MainNavigationDestination.ADD_CATEGORY.name)
fun NavController.navigateToInfoCategory(categoryId: Int) =
    navigate("${MainNavigationDestination.INFO_CATEGORY.name}/$categoryId")

fun NavController.navigateToGroupMemberChoose(previousScreenType: String) =
    navigate("${MainNavigationDestination.GROUP_MEMBER_CHOOSE.name}/$previousScreenType")

fun NavController.navigateToHistory() = navigate(MainNavigationDestination.HISTORY.name)

fun NavController.navigateToModifyProfile() =
    navigate(MainNavigationDestination.MODIFY_PROFILE.name)

fun NavController.navigateToSettingScreen() = navigate(MainNavigationDestination.SETTING.name)

fun NavController.navigateToAppThemeScreen(appThemeMode: String) =
    navigate("${MainNavigationDestination.APP_THEME.name}/$appThemeMode")

fun NavController.navigateToAddFollowerScreen() =
    navigate(MainNavigationDestination.ADD_FOLLOWER.name)

fun NavController.navigateToFollowTodoScreen(userId: Int) =
    navigate("${MainNavigationDestination.FOLLOW_TODO.name}/$userId")

// home navigation - composable route
fun NavGraphBuilder.timerScreen(
    setState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigateToConcentrationMode: () -> Unit,
    navigateToBreakMode: () -> Unit,
) {
    composable(route = BottomNavigationDestination.TIMER.name) {
        BackOnPressed()
        TimerRootScreen(
            setState = setState,
            navigateToConcentrationMode = navigateToConcentrationMode,
            navigateToBreakMode = navigateToBreakMode
        )
    }
}

private const val USER_ID = "userId"

internal class UserArgs(savedStateHandle: SavedStateHandle) {
    val userId: Int = checkNotNull(savedStateHandle[USER_ID]) {
        "Missing userId"
    }
}

fun NavGraphBuilder.todoScreen(
    navigateToAddCategory: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    isOffline: Boolean,
    navigateToFollowTodoScreen: (Int) -> Unit,
    navigateToMyTodoScreen: () -> Unit,
) {
    composable(route = BottomNavigationDestination.TODO.name) {
        BackOnPressed()
        TodoScreenRoute(
            navigateToAddCategory = navigateToAddCategory,
            navigateToCategory = navigateToCategory,
            navigateToHistory = navigateToHistory,
            isOffline = isOffline,
            navigateToFollowTodoScreen = navigateToFollowTodoScreen,
            navigateToMyTodoScreen = navigateToMyTodoScreen
        )
    }
}

fun NavGraphBuilder.followTodoScreen(
    navigateToBack: () -> Unit,
    navigateToFollowTodoScreen: (Int) -> Unit,
    navigateToMyTodoScreen: () -> Unit,
    isOffline: Boolean
) {
    composable(
        route = "${MainNavigationDestination.FOLLOW_TODO.name}/{$USER_ID}",
        arguments = listOf(navArgument(name = USER_ID) { type = NavType.IntType })
    ) {
        FollowTodoScreenRoute(
            navigateToFollowTodoScreen = navigateToFollowTodoScreen,
            navigateToBack = navigateToBack,
            isOffline = isOffline,
            navigateToMyTodoScreen = navigateToMyTodoScreen
        )
    }
}

fun NavGraphBuilder.followScreen(
    navigateToAddFollowerScreen: () -> Unit,
    navigateToFollowTodoScreen: (Int) -> Unit
) {
    composable(route = BottomNavigationDestination.FOLLOW.name) {
        BackOnPressed()
        FollowListScreen(
            navigateToAddFollowerScreen = navigateToAddFollowerScreen,
            navigateToFollowTodoScreen = navigateToFollowTodoScreen
        )
    }
}

fun NavGraphBuilder.myInfoScreen(navigateToSettingScreen: () -> Unit) {
    composable(route = BottomNavigationDestination.MY_INFO.name) {
        BackOnPressed()
        MyPageScreen(navigateToSettingScreen = navigateToSettingScreen)
    }
}

// main navigation - composable route
fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    isNetworkConnected: Boolean
) {
    composable(route = MainNavigationDestination.SPLASH.name) {
        SplashScreen(
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToHome,
            isNetworkConnected = isNetworkConnected
        )
    }
}

fun NavGraphBuilder.logInScreen(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
    onClickedOffline: () -> Unit,
    isOffline: Boolean
) {
    composable(route = MainNavigationDestination.LOG_IN.name) {
        LogInRoute(
            navigateToSignUp = navigateToSignUp,
            onClickedOffline = onClickedOffline,
            navigateToHome = navigateToHome,
            isOffline = isOffline
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    navController: NavHostController,
    navigateToHome: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.SIGN_UP.name) { navBackStackEntry ->
        val authNavBackStackEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(MainNavigationDestination.LOG_IN.name)
        }
        SignUpRoute(
            navBackStackEntry = authNavBackStackEntry,
            navigateToHome = navigateToHome,
            navigateToBack = navigateToBack
        )
    }
}

fun NavGraphBuilder.concentrationModeScreen(
    popBackStack: (destinationId: String, inclusive: Boolean) -> Unit,
    getState: (String) -> Int?,
    navigateToBreakMode: () -> Unit,
) {
    composable(route = MainNavigationDestination.CONCENTRATION_MODE.name) {
        ConcentrationTimerScreen(
            getState = getState,
            navigateToBreakMode = navigateToBreakMode,
            navigateToTimerHome = {
                popBackStack(MainNavigationDestination.HOME.name, false)
            }
        )
    }
}

fun NavGraphBuilder.breakModeScreen(mainNavController: NavController, getState: (String) -> Int?) {
    composable(route = MainNavigationDestination.BREAK_MODE.name) {
        BreakTimerScreen(
            navigateToTimerHome = {
                mainNavController.popBackStack(
                    MainNavigationDestination.HOME.name,
                    inclusive = false
                )
            },
            getState = getState
        )
    }
}

fun NavGraphBuilder.categoryScreen(
    navigateToAddCategory: () -> Unit,
    navigateToInfoCategory: (Int) -> Unit,
    navigateToBack: () -> Unit,
    isOffline: Boolean,
) {
    composable(route = MainNavigationDestination.CATEGORY.name) {
        CategoryScreenRoute(
            navigateToAddCategory = navigateToAddCategory,
            navigateToBack = navigateToBack,
            navigateToInfoCategory = navigateToInfoCategory,
            isOffline = isOffline
        )
    }
}

fun NavGraphBuilder.addCategoryScreen(
    navigateToGroupMemberChoose: (String) -> Unit,
    navigateToBack: () -> Unit,
    isOffline: Boolean
) {
    composable(route = MainNavigationDestination.ADD_CATEGORY.name) {
        CategoryAddScreenRoute(
            navigateToBack = navigateToBack,
            navigateToGroupMemberChoose = navigateToGroupMemberChoose,
            isOffline = isOffline
        )
    }
}

private const val CATEGORY_ID = "categoryId"

internal class CategoryArgs(savedStateHandle: SavedStateHandle) {
    val categoryId: Int = checkNotNull(savedStateHandle[CATEGORY_ID]) {
        "Missing categoryId"
    }
}

fun NavGraphBuilder.infoCategoryScreen(
    navigateToBack: () -> Unit,
    isOffline: Boolean,
    navigateToFollowTodoScreen: (Int) -> Unit,
) {
    composable(
        route = "${MainNavigationDestination.INFO_CATEGORY.name}/{$CATEGORY_ID}",
        arguments = listOf(navArgument(name = CATEGORY_ID) { type = NavType.IntType })
    ) {
        CategoryInfoScreenRoute(
            navigateToBack = navigateToBack,
            isOffline = isOffline,
            navigateToFollowTodoScreen = navigateToFollowTodoScreen
        )
    }
}

fun NavGraphBuilder.historyScreen(
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.HISTORY.name) {
        HistoryRoute(navigateToBack = navigateToBack)
    }
}

private const val PREVIOUS_SCREEN_TYPE = "previousScreenType"

fun NavGraphBuilder.groupMemberChooseScreen(
    navController: NavController,
    navigateToBack: () -> Unit
) {
    composable(
        route = "${MainNavigationDestination.GROUP_MEMBER_CHOOSE.name}/{$PREVIOUS_SCREEN_TYPE}",
        arguments = listOf(navArgument(name = PREVIOUS_SCREEN_TYPE) { type = NavType.StringType })
    ) { backStackEntry ->
        val parentEntry = remember {
            navController.previousBackStackEntry
        }
        val previousScreenType =
            backStackEntry.arguments?.getString(PREVIOUS_SCREEN_TYPE) ?: return@composable

        if (parentEntry != null) {
            GroupMemberChooseRoute(
                navBackStackEntry = parentEntry,
                navigateToBack = navigateToBack,
                previousScreenType = previousScreenType
            )
        }
    }
}

fun NavGraphBuilder.modifyProfileScreen(navController: NavController) {
    composable(route = MainNavigationDestination.MODIFY_PROFILE.name) { navBackStackEntry ->
        val myInfoScreenEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(MainNavigationDestination.MY_PAGE.name)
        }
        ModifyProfileScreen(
            navController = navController,
            navBackStackEntry = myInfoScreenEntry
        )
    }
}

fun NavGraphBuilder.settingScreen(
    navigateToModifyProfileScreen: () -> Unit,
    navigateToAppThemeScreen: (String) -> Unit,
    popBackStack: () -> Unit
) {
    composable(route = MainNavigationDestination.SETTING.name) {
        SettingScreen(
            navigateToModifyProfileScreen = navigateToModifyProfileScreen,
            navigateToAppThemeScreen = navigateToAppThemeScreen,
            navigateToTermsOfUseScreen = { /*TODO*/ },
            navigateToPrivacyPolicyScreen = {},
            popBackStack = { popBackStack() }
        )
    }
}

private const val APP_THEME_MODE = "AppThemeMode"

fun NavGraphBuilder.appThemeScreen(popBackStack: () -> Unit) {
    composable(
        route = "${MainNavigationDestination.APP_THEME.name}/{$APP_THEME_MODE}",
        arguments = listOf(navArgument(name = APP_THEME_MODE) { type = NavType.StringType })
    ) { navBackStackEntry ->
        val appThemeMode = navBackStackEntry.arguments?.getString(APP_THEME_MODE) ?: ""

        AppThemeScreen(
            initialSelectedMode = appThemeMode,
            popBackStack = { popBackStack() }
        )
    }
}

fun NavGraphBuilder.addFollowerScreen(
    popBackToFollowScreen: () -> Unit,
    navigateToFollowTodoScreen: (Int) -> Unit
) {
    composable(route = MainNavigationDestination.ADD_FOLLOWER.name) {
        AddFollowerScreen(
            popBackToFollowScreen = popBackToFollowScreen,
            navigateToFollowTodoScreen = navigateToFollowTodoScreen
        )
    }
}