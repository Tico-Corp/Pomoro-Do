package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.tico.pomorodo.ui.common.view.BREAK_TIME
import com.tico.pomorodo.ui.common.view.CONCENTRATION_TIME
import com.tico.pomorodo.ui.home.view.AppState

@Composable
fun AppNavHost(modifier: Modifier = Modifier, appState: AppState) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = MainNavigationDestination.HOME.name,
        modifier = modifier
    ) {
        splashScreen(
            navigateToLogin = navController::navigateToLogIn,
            navigateToHome = navController::navigateToHome
        )

        logInScreen(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToHome = navController::navigateToHome,
            isOffline = appState.isOffline.value
        )
        signUpScreen(
            navController = navController,
            navigateToHome = navController::navigateToHome,
            navigateToBack = navController::popBackStack
        )
        navigation(
            route = MainNavigationDestination.HOME.name,
            startDestination = BottomNavigationDestination.TODO.name
        ) {
            timerScreen(
                setState = { concentrationTime, breakTime ->
                    navController.setState(CONCENTRATION_TIME, concentrationTime)
                    navController.setState(BREAK_TIME, breakTime)
                },
                navigateToConcentrationMode = navController::navigateToConcentrationMode,
                navigateToBreakMode = navController::navigateToBreakMode
            )
            todoScreen(
                navigateToCategory = navController::navigateToCategory,
                navigateToAddCategory = navController::navigateToAddCategory,
                navigateToHistory = navController::navigateToHistory
            )
            followScreen(navigateToAddFollowerScreen = navController::navigateToAddFollowerScreen)
            myInfoScreen(
                navigateToModifyProfile = navController::navigateToModifyProfile,
                navigateToSettingScreen = navController::navigateToSettingScreen
            )
            concentrationModeScreen(
                popBackStack = navController::popBackStack,
                getState = navController::getState,
                navigateToBreakMode = navController::navigateToBreakMode
            )
            breakModeScreen(
                mainNavController = navController,
                getState = navController::getState
            )

            categoryScreen(
                navigateToAddCategory = navController::navigateToAddCategory,
                navigateToBack = navController::popBackStack,
                navigateToInfoCategory = { categoryId ->
                    navController.navigateToInfoCategory(
                        categoryId = categoryId
                    )
                }
            )
            addCategoryScreen(
                navigateToGroupMemberChoose = navController::navigateToGroupMemberChoose,
                navigateToBack = navController::popBackStack
            )
            infoCategoryScreen(
                navigateToBack = navController::popBackStack
            )
            groupMemberChooseScreen(
                navController = navController,
                navigateToBack = navController::popBackStack
            )

            historyScreen(navigateToBack = navController::popBackStack)

            modifyProfileScreen(navController = navController)

            settingScreen(
                navigateToAppThemeScreen = navController::navigateToAppThemeScreen,
                popBackStack = navController::popBackStack
            )
            appThemeScreen(popBackStack = navController::popBackStack)

            addFollowerScreen(popBackToFollowScreen = appState.navController::popBackStack)
        }
    }
}