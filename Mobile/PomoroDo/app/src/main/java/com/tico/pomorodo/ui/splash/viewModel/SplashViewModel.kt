package com.tico.pomorodo.ui.splash.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.auth.IsAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ReissueTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveRefreshTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ValidateTokenUseCase
import com.tico.pomorodo.ui.auth.viewModel.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val isAccessTokenUseCase: IsAccessTokenUseCase,
    private val validateTokenUseCase: ValidateTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase
) : ViewModel() {

    private var _authState = MutableStateFlow<AuthState>(AuthState.LOADING)
    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    private var _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

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
        validateTokenUseCase(ACCESS).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.status == NetworkConstants.SUCCESS_CODE) {
                        _authState.value = AuthState.SUCCESS_LOGIN
                        _isLoading.value = false
                    }
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }

                is Resource.Failure.Error -> {
                    if (result.code == NetworkConstants.INVALID_ACCESS_TOKEN) {
                        getAccessTokenAsRefreshToken()
                    }
                    Log.e("SplashViewModel", "checkAccessToken: ${result.code} ${result.message}")
                }

                is Resource.Failure.Exception -> {
                    Log.e("SplashViewModel", "checkAccessToken: ${result.message}")
                }
            }
        }
    }

    private fun getAccessTokenAsRefreshToken() = viewModelScope.launch {
        reissueTokenUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.status == NetworkConstants.SUCCESS_CODE) {
                        saveRefreshToken(result.data.data.refreshToken)
                        saveAccessToken(result.data.data.accessToken)
                        _authState.value = AuthState.SUCCESS_LOGIN
                        _isLoading.value = false
                    }
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }

                is Resource.Failure.Error -> {
                    if (result.code == NetworkConstants.INVALID_REFRESH_TOKEN) {
                        _authState.value = AuthState.NEED_LOGIN
                    }
                    Log.e(
                        "SplashViewModel",
                        "getAccessTokenAsRefreshToken: ${result.code} ${result.message}"
                    )
                }

                is Resource.Failure.Exception -> {
                    _authState.value = AuthState.NEED_LOGIN
                    Log.e("SplashViewModel", "getAccessTokenAsRefreshToken: ${result.message}")
                }
            }
        }
    }

    private fun saveRefreshToken(token: String) {
        viewModelScope.launch {
            saveRefreshTokenUseCase(token)
        }
    }

    private fun saveAccessToken(token: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(token)
        }
    }

    companion object {
        private const val ACCESS = "ACCESS"
    }
}