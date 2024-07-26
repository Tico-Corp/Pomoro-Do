package com.tico.pomorodo.ui.splash.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.domain.usecase.IsAccessTokenUseCase
import com.tico.pomorodo.ui.auth.viewModel.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isAccessTokenUseCase: IsAccessTokenUseCase,
) : ViewModel() {

    private var _authState = MutableStateFlow<AuthState>(AuthState.LOADING)
    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    init {
        fetchIsAccessToken()
    }

    private fun fetchIsAccessToken() = viewModelScope.launch {
        val result = isAccessTokenUseCase()
        if (result) {
            checkAccessToken()
        } else {
            _authState.value = AuthState.NEED_LOGIN
        }
    }

    private fun checkAccessToken() = viewModelScope.launch {
        // accessToken의 유효성 검사하는 로직
    }
}