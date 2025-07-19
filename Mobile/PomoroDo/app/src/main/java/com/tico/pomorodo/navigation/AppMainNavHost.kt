package com.tico.pomorodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.tico.pomorodo.ui.common.view.BREAK_TIME
import com.tico.pomorodo.ui.common.view.CONCENTRATION_TIME
import com.tico.pomorodo.ui.home.view.AppState

@Composable
fun AppMainNavHost(modifier: Modifier = Modifier, appState: AppState) {
    val mainNavController = appState.mainNavController
    NavHost(
        navController = mainNavController,
        startDestination = MainNavigationDestination.HOME.name,
        modifier = modifier
    ) {
        splashScreen(
            navigateToLogin = mainNavController::navigateToLogIn,
            navigateToHome = mainNavController::navigateToHome
        )

        logInScreen(
            navigateToSignUp = mainNavController::navigateToSignUp,
            navigateToHome = mainNavController::navigateToHome,
        )
        signUpScreen(
            navController = mainNavController,
            navigateToHome = mainNavController::navigateToHome,
            navigateToBack = mainNavController::popBackStack
        )

        homeScreen(
            appState = appState,
            setTimerState = { concentrationTime, breakTime ->
                mainNavController.setState(CONCENTRATION_TIME, concentrationTime)
                mainNavController.setState(BREAK_TIME, breakTime)
            },
            navigateToConcentrationMode = mainNavController::navigateToConcentrationMode,
            navigateToBreakMode = mainNavController::navigateToBreakMode,
            navigateToCategory = mainNavController::navigateToCategory,
            navigateToAddCategory = mainNavController::navigateToAddCategory,
            navigateToHistory = mainNavController::navigateToHistory,
            navigateToModifyProfile = mainNavController::navigateToModifyProfile,
            navigateToSettingScreen = mainNavController::navigateToSettingScreen,
            navigateToAddFollowerScreen = mainNavController::navigateToAddFollowerScreen,
        )

        concentrationModeScreen(
            popBackStack = mainNavController::popBackStack,
            getState = mainNavController::getState,
            navigateToBreakMode = mainNavController::navigateToBreakMode
        )
        breakModeScreen(
            mainNavController = mainNavController,
            getState = mainNavController::getState
        )

        categoryScreen(
            navigateToAddCategory = mainNavController::navigateToAddCategory,
            navigateToBack = mainNavController::popBackStack,
            navigateToInfoCategory = { categoryId ->
                mainNavController.navigateToInfoCategory(
                    categoryId = categoryId
                )
            }
        )
        addCategoryScreen(
            navigateToGroupMemberChoose = mainNavController::navigateToGroupMemberChoose,
            navigateToBack = mainNavController::popBackStack
        )
        infoCategoryScreen(
            navigateToGroupMemberChoose = mainNavController::navigateToGroupMemberChoose,
            navigateToBack = mainNavController::popBackStack
        )
        groupMemberChooseScreen(
            navController = mainNavController,
            navigateToBack = mainNavController::popBackStack
        )

        historyScreen(navigateToBack = mainNavController::popBackStack)

        modifyProfileScreen(navController = mainNavController)

        settingScreen(
            navigateToAppThemeScreen = mainNavController::navigateToAppThemeScreen,
            popBackStack = mainNavController::popBackStack
        )
        appThemeScreen(popBackStack = mainNavController::popBackStack)

        addFollowerScreen(popBackToFollowScreen = appState.mainNavController::popBackStack)
    }
}