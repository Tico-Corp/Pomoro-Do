package com.tico.pomorodo.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.navigation.addCategoryScreen
import com.tico.pomorodo.navigation.addFollowerScreen
import com.tico.pomorodo.navigation.appThemeScreen
import com.tico.pomorodo.navigation.breakModeScreen
import com.tico.pomorodo.navigation.categoryScreen
import com.tico.pomorodo.navigation.concentrationModeScreen
import com.tico.pomorodo.navigation.getState
import com.tico.pomorodo.navigation.groupMemberChooseScreen
import com.tico.pomorodo.navigation.historyScreen
import com.tico.pomorodo.navigation.homeScreen
import com.tico.pomorodo.navigation.infoCategoryScreen
import com.tico.pomorodo.navigation.logInScreen
import com.tico.pomorodo.navigation.modifyProfileScreen
import com.tico.pomorodo.navigation.navigateToAddCategory
import com.tico.pomorodo.navigation.navigateToAddFollowerScreen
import com.tico.pomorodo.navigation.navigateToAppThemeScreen
import com.tico.pomorodo.navigation.navigateToBreakMode
import com.tico.pomorodo.navigation.navigateToCategory
import com.tico.pomorodo.navigation.navigateToConcentrationMode
import com.tico.pomorodo.navigation.navigateToGroupMemberChoose
import com.tico.pomorodo.navigation.navigateToHistory
import com.tico.pomorodo.navigation.navigateToHome
import com.tico.pomorodo.navigation.navigateToInfoCategory
import com.tico.pomorodo.navigation.navigateToLogIn
import com.tico.pomorodo.navigation.navigateToModifyProfile
import com.tico.pomorodo.navigation.navigateToSettingScreen
import com.tico.pomorodo.navigation.navigateToSignUp
import com.tico.pomorodo.navigation.setState
import com.tico.pomorodo.navigation.settingScreen
import com.tico.pomorodo.navigation.signUpScreen
import com.tico.pomorodo.navigation.splashScreen
import com.tico.pomorodo.ui.common.view.BREAK_TIME
import com.tico.pomorodo.ui.common.view.CONCENTRATION_TIME
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = PomoroDoTheme.colorScheme.background
    ) {
        val mainNavController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            NavHost(
                navController = mainNavController,
                startDestination = MainNavigationDestination.HOME.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    )
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
                    setTimerState = { concentrationTime, breakTime ->
                        mainNavController.setState(CONCENTRATION_TIME, concentrationTime)
                        mainNavController.setState(BREAK_TIME, breakTime)
                    },
                    navigateToConcentrationMode = mainNavController::navigateToConcentrationMode,
                    navigateToCategory = mainNavController::navigateToCategory,
                    navigateToAddCategory = mainNavController::navigateToAddCategory,
                    navigateToHistory = mainNavController::navigateToHistory,
                    navigateToModifyProfile = mainNavController::navigateToModifyProfile,
                    navigateToSettingScreen = mainNavController::navigateToSettingScreen,
                    navigateToAddFollowerScreen = mainNavController::navigateToAddFollowerScreen,
                )

                concentrationModeScreen(
                    getState = mainNavController::getState,
                    mainNavController::navigateToBreakMode
                )
                breakModeScreen(navController = mainNavController)

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
                    navigateToCategory = mainNavController::navigateToCategory,
                    navigateToGroupMemberChoose = mainNavController::navigateToGroupMemberChoose,
                    navigateToBack = mainNavController::popBackStack
                )
                infoCategoryScreen(
                    navigateToCategory = mainNavController::navigateToCategory,
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

                addFollowerScreen(popBackToFollowScreen = mainNavController::popBackStack)
            }
        }
    }
}