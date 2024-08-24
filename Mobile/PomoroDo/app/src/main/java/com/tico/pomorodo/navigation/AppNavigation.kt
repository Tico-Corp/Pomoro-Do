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
import com.tico.pomorodo.ui.category.view.AddCategoryScreenRoute
import com.tico.pomorodo.ui.category.view.CategoryScreenRoute
import com.tico.pomorodo.ui.category.view.GroupMemberChooseRoute
import com.tico.pomorodo.ui.category.view.InfoCategoryScreenRoute
import com.tico.pomorodo.ui.history.view.HistoryRoute
import com.tico.pomorodo.ui.home.view.HomeScreen
import com.tico.pomorodo.ui.member.view.FollowListScreen
import com.tico.pomorodo.ui.member.view.ModifyProfileScreen
import com.tico.pomorodo.ui.member.view.MyPageScreen
import com.tico.pomorodo.ui.setting.view.SettingScreen
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
fun NavController.navigateToLogIn() = navigate(MainNavigationDestination.LogIn.name) {
    popUpTo(MainNavigationDestination.Splash.name) { inclusive = true }
}

fun NavController.navigateToSignUp() = navigate(MainNavigationDestination.SignUp.name)

fun NavController.navigateToHome() {
    navigate(MainNavigationDestination.Home.name) {
        popUpTo(graph.id) { inclusive = true }
    }
}

fun NavController.navigateToConcentrationMode() =
    navigate(MainNavigationDestination.ConcentrationMode.name)

fun NavController.navigateToBreakMode() =
    navigate(MainNavigationDestination.BreakMode.name)

fun NavController.navigateToCategory() = navigate(MainNavigationDestination.Category.name)
fun NavController.navigateToAddCategory() = navigate(MainNavigationDestination.AddCategory.name)
fun NavController.navigateToInfoCategory(categoryId: Int) =
    navigate("${MainNavigationDestination.InfoCategory.name}/$categoryId")

fun NavController.navigateToGroupMemberChoose(previousScreenType: String) =
    navigate("${MainNavigationDestination.GroupMemberChoose.name}/$previousScreenType")

fun NavController.navigateToHistory() = navigate(MainNavigationDestination.History.name)

fun NavController.navigateToModifyProfile() = navigate(MainNavigationDestination.ModifyProfile.name)

fun NavController.navigateToFollowListScreen() =
    navigate(MainNavigationDestination.FollowListScreen.name)

fun NavController.navigateToSettingScreen() = navigate(MainNavigationDestination.SettingScreen.name)


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

fun NavGraphBuilder.myInfoScreen(
    navigateToModifyProfile: () -> Unit,
    navigateToFollowListScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
) {
    composable(route = BottomNavigationDestination.MyInfo.name) {
        MyPageScreen(
            navigateToModifyProfile = navigateToModifyProfile,
            navigateToFollowListScreen = navigateToFollowListScreen,
            navigateToSettingScreen = navigateToSettingScreen
        )
    }
}

// main navigation - composable route
fun NavGraphBuilder.splashScreen(navigateToLogin: () -> Unit, navigateToHome: () -> Unit) {
    composable(route = MainNavigationDestination.Splash.name) {
        SplashScreen(navigateToLogin = navigateToLogin, navigateToHome = navigateToHome)
    }
}

fun NavGraphBuilder.logInScreen(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable(route = MainNavigationDestination.LogIn.name) {
        LogInRoute(
            navigateToSignUp = navigateToSignUp,
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    navController: NavHostController,
    navigateToHome: () -> Unit,
    navigateToBack: () -> Unit
) {
    composable(route = MainNavigationDestination.SignUp.name) { navBackStackEntry ->
        val authNavBackStackEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(MainNavigationDestination.LogIn.name)
        }
        SignUpRoute(
            navBackStackEntry = authNavBackStackEntry,
            navigateToHome = navigateToHome,
            navigateToBack = navigateToBack
        )
    }
}

fun NavGraphBuilder.homeScreen(
    setTimerState: (concentrationTime: Int, breakTime: Int) -> Unit,
    navigateToConcentrationMode: () -> Unit,
    navigateToCategory: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToModifyProfile: () -> Unit,
    navigateToFollowListScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit
) {
    composable(route = MainNavigationDestination.Home.name) {
        HomeScreen(
            setTimerState = setTimerState,
            navigateToConcentrationMode = navigateToConcentrationMode,
            navigateToCategory = navigateToCategory,
            navigateToAddCategory = navigateToAddCategory,
            navigateToHistory = navigateToHistory,
            navigateToModifyProfile = navigateToModifyProfile,
            navigateToFollowListScreen = navigateToFollowListScreen,
            navigateToSettingScreen = navigateToSettingScreen
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
    navigateToInfoCategory: (Int) -> Unit,
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
    navigateToCategory: () -> Unit,
    navigateToGroupMemberChoose: (String) -> Unit,
    navigateToBack: () -> Unit,
) {
    composable(route = MainNavigationDestination.AddCategory.name) {
        AddCategoryScreenRoute(
            navigateToCategory = navigateToCategory,
            navigateToBack = navigateToBack,
            navigateToGroupMemberChoose = navigateToGroupMemberChoose,
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
    navigateToCategory: () -> Unit,
    navigateToGroupMemberChoose: (String) -> Unit,
    navigateToBack: () -> Unit
) {
    composable(
        route = "${MainNavigationDestination.InfoCategory.name}/{$CATEGORY_ID}",
        arguments = listOf(navArgument(name = CATEGORY_ID) { type = NavType.IntType })
    ) {
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

private const val PREVIOUS_SCREEN_TYPE = "previousScreenType"

fun NavGraphBuilder.groupMemberChooseScreen(
    navController: NavController,
    navigateToBack: () -> Unit
) {
    composable(
        route = "${MainNavigationDestination.GroupMemberChoose.name}/{$PREVIOUS_SCREEN_TYPE}",
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
    composable(route = MainNavigationDestination.ModifyProfile.name) { navBackStackEntry ->
        ModifyProfileScreen(navController = navController, navBackStackEntry = navBackStackEntry)
    }
}

fun NavGraphBuilder.followListScreen() {
    composable(route = MainNavigationDestination.FollowListScreen.name) {
        FollowListScreen()
    }
}

fun NavGraphBuilder.settingScreen(navController: NavController) {
    composable(route = MainNavigationDestination.SettingScreen.name) {
        SettingScreen(
            navigateToModifyProfileScreen = { /*TODO*/ },
            navigateToAppThemeScreen = { /*TODO*/ },
            navigateToTermsOfUseScreen = { /*TODO*/ },
            navigateToPrivacyPolicyScreen = {},
            popBackStack = { navController.popBackStack() }
        )
    }
}