package com.tico.pomorodo.ui.auth.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.ClearAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.ClearIdTokenUseCase
import com.tico.pomorodo.domain.usecase.GetAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.GetIdTokenUseCase
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
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val clearAccessTokenUseCase: ClearAccessTokenUseCase,
    private val saveIdTokenUseCase: SaveIdTokenUseCase,
    private val getIdTokenUseCase: GetIdTokenUseCase,
    private val clearIdTokenUseCase: ClearIdTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase
) : ViewModel() {

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
                if (value.data.status == NetworkConstants.SUCCESS_STATUS) {
                    saveAccessToken(value.data.data.accessToken)
                    Log.d("TAG", "requestJoin: success")
                }
            }

            is Resource.Failure.Error -> {
                if (value.code == NetworkConstants.USER_NOT_FOUND) {
                    Log.e("TAG", "requestLogin: ${value.message}")
                } else {
                    Log.e("TAG", "requestLogin: code:${value.code}, message: ${value.message}")
                }
            }

            is Resource.Failure.Exception -> {
                Log.e("TAG", "requestLogin: message: ${value.message}")
            }
        }
    }

    fun requestJoin() = viewModelScope.launch {
        when (val value = joinUseCase(UserInfoRequestBody(nickname = name.value))) {
            is Resource.Success -> {
                if (value.data.status == 201) {
                    saveAccessToken(value.data.data.accessToken)
                    Log.d("TAG", "requestJoin: success")
                }
            }

            is Resource.Failure.Error -> {
                Log.e("TAG", "requestLogin: code:${value.code}, message: ${value.message}")
            }

            is Resource.Failure.Exception -> {
                Log.e("TAG", "requestLogin: message: ${value.message}")
            }
        }
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

    fun clearToken() {
        viewModelScope.launch {
            clearAccessTokenUseCase()
        }
    }
}