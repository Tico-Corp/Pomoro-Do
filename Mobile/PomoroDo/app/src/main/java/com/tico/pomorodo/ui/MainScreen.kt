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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.ui.home.view.BottomBar
import com.tico.pomorodo.ui.home.view.rememberAppState
import com.tico.pomorodo.ui.theme.PomoroDoTheme

@Composable
fun MainScreen() {
    val appState = rememberAppState()
    val homeTabs = appState.bottomNavigationDestinationList.map { it.name }.toSet()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            val currentRoute = appState.currentDestination?.route
            if (currentRoute in homeTabs) {
                BottomBar(appState)
            }
        },
        containerColor = PomoroDoTheme.colorScheme.background
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