package com.tico.pomorodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tico.pomorodo.ui.MainScreen
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomoroDoTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}