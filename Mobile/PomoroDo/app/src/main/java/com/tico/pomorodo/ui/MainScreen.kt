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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tico.pomorodo.R
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.navigation.BottomNavigationDestination
import com.tico.pomorodo.navigation.MainNavigationDestination
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.home.view.BottomBar
import com.tico.pomorodo.ui.home.view.rememberAppState
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MainScreen() {
    val appState = rememberAppState()
    val context = LocalContext.current
    val allTabs = appState.bottomNavigationDestinationList
    val homeTabsRoutes = allTabs.map { it.name }.toSet()
    val currentRoute = appState.currentDestination?.route

    LaunchedEffect(appState.isOffline.value, currentRoute) {
        val isNowFollow =
            currentRoute?.startsWith(BottomNavigationDestination.FOLLOW.name) == true ||
                    currentRoute?.startsWith(MainNavigationDestination.FOLLOW_TODO.name) == true
        if (appState.isOffline.value && isNowFollow) {
            appState.navigateToDestination(BottomNavigationDestination.TIMER)
        }
    }

    LaunchedEffect(appState.isNetworkConnected.value) {
        if (appState.isNetworkConnected.value) {
            if (appState.isOffline.value) {
                val result = appState.snackBarHostState.showSnackbar(
                    withDismissAction = true,
                    message = context.getString(R.string.title_connected_network_change_online_mode),
                    actionLabel = context.getString(R.string.content_change_online_mode),
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    // TODO: Sync 화면으로 이동
                    appState.setIsOffline(false)
                }
            }
        } else {
            context.executeToast(R.string.title_disconnected_network)
            appState.setIsOffline(true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (currentRoute in homeTabsRoutes) {
                BottomBar(
                    currentDestination = appState.currentDestination,
                    navigateToDestination = appState::navigateToDestination,
                    isOffline = appState.isOffline.value,
                    destinations = allTabs
                )
            }
        },
        containerColor = PomoroDoTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = appState.snackBarHostState, snackbar = {
                Snackbar(
                    snackbarData = it,
                    containerColor = PomoroDoTheme.colorScheme.gray10,
                    contentColor = PomoroDoTheme.colorScheme.onPrimary,
                    actionColor = PomoroDoTheme.colorScheme.primaryContainer,
                    dismissActionContentColor = PomoroDoTheme.colorScheme.gray50
                )
            })
        }
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
            appState = appState
        )
    }
}