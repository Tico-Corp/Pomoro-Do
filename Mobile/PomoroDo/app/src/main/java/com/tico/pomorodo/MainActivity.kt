package com.tico.pomorodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.tico.pomorodo.ui.MainScreen
import com.tico.pomorodo.ui.home.view.rememberAppState
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState(mainViewModel)

            PomoroDoTheme {
                // A surface container using the 'background' color from the theme
                MainScreen(appState)
            }
        }
    }
}