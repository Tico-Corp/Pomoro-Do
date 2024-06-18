package com.tico.pomorodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tico.pomorodo.navigation.AppNavHost
import com.tico.pomorodo.navigation.TIMER_ROUTE
import com.tico.pomorodo.ui.AppState
import com.tico.pomorodo.ui.common.view.BottomBar
import com.tico.pomorodo.ui.theme.PomoroDoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomoroDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PomoroDoTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val appState = AppState(navController)

                    Scaffold(
                        bottomBar = { BottomBar(appState) },
                        containerColor = PomoroDoTheme.colorScheme.background
                    ) { innerPadding ->
                        AppNavHost(
                            appState = appState,
                            Modifier.padding(innerPadding),
                            startDestination = TIMER_ROUTE
                        )
                    }
                }
            }
        }
    }
}
