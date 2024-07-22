package com.tico.pomorodo.ui.auth.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.JoinUseCase
import com.tico.pomorodo.domain.usecase.LoginUseCase
import com.tico.pomorodo.domain.usecase.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveIdTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveIdTokenUseCase: SaveIdTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase
) : ViewModel() {

    private var _authState = MutableStateFlow<AuthState>(AuthState.NEED_LOGIN)
    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    private var _name = MutableStateFlow<String>("")
    val name: StateFlow<String>
        get() = _name.asStateFlow()

    private var _profile = MutableStateFlow<Uri?>(null)
    val profile: StateFlow<Uri?>
        get() = _profile.asStateFlow()

    fun setName(inputText: String) {
        _name.value = inputText
    }

    fun setProfile(url: Uri?) {
        _profile.value = url
    }

    fun requestLogin() = viewModelScope.launch {
        when (val value = loginUseCase()) {
            is Resource.Success -> {
                if (value.data.status == NetworkConstants.SUCCESS_LOGIN) {
                    saveAccessToken(value.data.data.accessToken)
                    _authState.value = AuthState.SUCCESS_LOGIN
                }
            }

            is Resource.Failure.Error -> {
                if (value.code == NetworkConstants.USER_NOT_FOUND) {
                    _authState.value = AuthState.NEED_JOIN
                } else {
                    Log.e("AuthViewModel", "requestLogin: ${value.code} ${value.message}")
                }
            }

            is Resource.Failure.Exception -> {
                Log.e("AuthViewModel", "requestLogin: ${value.message}")
            }
        }
    }

    fun requestJoin() = viewModelScope.launch {
        _authState.value = AuthState.SUCCESS_JOIN
        // 회원 가입하는 로직
        /*when (val value = joinUseCase(UserInfoRequestBody(nickname = name.value))) {
            is Resource.Success -> {
                if (value.data.status == NetworkConstants.SUCCESS_JOIN) {
                    saveAccessToken(value.data.data.accessToken)
                    _authState.value = AuthState.SUCCESS_JOIN
                }
            }

            is Resource.Failure.Error -> {
                Log.e("AuthViewModel", "requestJoin: ${value.code} ${value.message}")
            }

            is Resource.Failure.Exception -> {
                Log.e("AuthViewModel", "requestJoin: ${value.message}")
            }
        }*/
    }

    private fun saveAccessToken(token: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(token)
        }
    }

    fun saveIdToken(token: String) {
        viewModelScope.launch {
            saveIdTokenUseCase(token)
        }
    }
}