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
        startDestination = MainNavigationDestination.SPLASH.name,
        modifier = modifier
    ) {
        splashScreen(
            navigateToLogin = navController::navigateToLogIn,
            navigateToHome = navController::navigateToHome
        )

        logInScreen(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToHome = navController::navigateToHome,
            onClickedOffline = {
                navController.navigateToHome()
                appState.setIsOffline(true)
            },
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
                navigateToHistory = navController::navigateToHistory,
                isOffline = appState.isOffline.value
            )
            followScreen(navigateToAddFollowerScreen = navController::navigateToAddFollowerScreen)
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
                },
                isOffline = appState.isOffline.value
            )
            addCategoryScreen(
                navigateToGroupMemberChoose = navController::navigateToGroupMemberChoose,
                navigateToBack = navController::popBackStack,
                isOffline = appState.isOffline.value
            )
            infoCategoryScreen(
                navigateToBack = navController::popBackStack,
                isOffline = appState.isOffline.value
            )
            groupMemberChooseScreen(
                navController = navController,
                navigateToBack = navController::popBackStack
            )

            historyScreen(navigateToBack = navController::popBackStack)

            navigation(
                route = MainNavigationDestination.MY_PAGE.name,
                startDestination = BottomNavigationDestination.MY_INFO.name
            ) {
                myInfoScreen(
                    navigateToSettingScreen = navController::navigateToSettingScreen
                )

                settingScreen(
                    navigateToModifyProfileScreen = navController::navigateToModifyProfile,
                    navigateToAppThemeScreen = navController::navigateToAppThemeScreen,
                    popBackStack = navController::popBackStack
                )
            }

            modifyProfileScreen(navController = navController)

            appThemeScreen(popBackStack = navController::popBackStack)

            addFollowerScreen(popBackToFollowScreen = appState.navController::popBackStack)
        }
    }
}