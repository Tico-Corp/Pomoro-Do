package com.tico.pomorodo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun PomoroDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalPomoroDoTypography provides Typography,
        LocalPomoroDoColorScheme provides colorScheme
    ) {
        Surface(content = content)
    }
}

object PomoroDoTheme {
    val typography: PomoroDoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPomoroDoTypography.current

    val colorScheme: PomoroDoColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPomoroDoColorScheme.current
}