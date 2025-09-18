package com.tico.pomorodo.ui.splash.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.auth.viewModel.AuthState
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTitle
import com.tico.pomorodo.ui.splash.viewModel.SplashViewModel
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    isNetworkConnected: Boolean
) {
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.attemptAutoLogin(isNetworkConnected)
    }

    LaunchedEffect(authState) {
        when (authState) {
            AuthState.SUCCESS_LOGIN -> {
                delay(3000)
                navigateToHome()
            }

            AuthState.NEED_LOGIN, AuthState.OFFLINE -> {
                delay(3000)
                navigateToLogin()
            }

            else -> {
                Unit
            }
        }
    }

    PomoroDoTheme {
        Surface(color = PomoroDoTheme.colorScheme.surface) {
            Icon(
                imageVector = IconPack.IcTitle,
                contentDescription = stringResource(R.string.content_ic_title),
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                tint = Color.Unspecified
            )
        }
    }
}