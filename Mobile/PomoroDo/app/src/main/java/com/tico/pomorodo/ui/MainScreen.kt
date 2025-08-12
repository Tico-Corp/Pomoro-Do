package com.tico.pomorodo.ui

import android.widget.Toast
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
import com.tico.pomorodo.ui.home.view.BottomBar
import com.tico.pomorodo.ui.home.view.rememberAppState
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MainScreen() {
    val appState = rememberAppState()
    val homeTabs = appState.bottomNavigationDestinationList.map { it.name }.toSet()
    val context = LocalContext.current

    LaunchedEffect(key1 = appState.isNetworkConnected.value, key2 = appState.isOffline.value) {
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
            Toast.makeText(
                appState.navController.context,
                context.getString(R.string.title_disconnected_network),
                Toast.LENGTH_LONG
            ).show()
            appState.setIsOffline(true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            val currentRoute = appState.currentDestination?.route
            if (currentRoute in homeTabs) {
                BottomBar(appState)
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