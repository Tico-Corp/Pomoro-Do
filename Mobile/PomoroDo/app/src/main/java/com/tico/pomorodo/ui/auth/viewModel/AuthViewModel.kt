package com.tico.pomorodo.ui.auth.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.installations.FirebaseInstallations
import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.data.model.NameErrorType
import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.GetFIDUseCase
import com.tico.pomorodo.domain.usecase.JoinUseCase
import com.tico.pomorodo.domain.usecase.LoginUseCase
import com.tico.pomorodo.domain.usecase.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveFIDUseCase
import com.tico.pomorodo.domain.usecase.SaveIdTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveIdTokenUseCase: SaveIdTokenUseCase,
    private val saveFIDUseCase: SaveFIDUseCase,
    private val getFIDUseCase: GetFIDUseCase,
    private val loginUseCase: LoginUseCase,
    private val joinUseCase: JoinUseCase
) : ViewModel() {

    private var _authState = MutableStateFlow<AuthState>(AuthState.NEED_LOGIN)
    val authState: StateFlow<AuthState>
        get() = _authState.asStateFlow()

    private var _name = MutableStateFlow<String>("")
    val name: StateFlow<String>
        get() = _name.asStateFlow()

    private var profileImageType = ProfileImageType.DEFAULT

    private var profile = MutableStateFlow<File?>(null)

    private var _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?>
        get() = _profileUri.asStateFlow()

    init {
        viewModelScope.launch {
            val fid = getFIDUseCase()
            if (fid == null) {
                getAndSaveFid()
            }
        }
    }

    private fun getAndSaveFid() {
        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fid = task.result
                viewModelScope.launch { saveFIDUseCase(fid) }
            } else {
                Log.e(TAG, "Failed to get FID", task.exception)
            }
        }
    }

    fun setName(inputText: String) {
        _name.value = inputText
    }

    fun nameValidate(inputText: String): NameErrorType {
        val regex = "[^ㄱ-ㅎ가-힣a-zA-Z0-9_]".toRegex()
        return if (inputText.length !in NICKNAME_MIN_RANGE..NICKNAME_MAX_RANGE) {
            NameErrorType.RANGE_ERROR
        } else if (regex.containsMatchIn(inputText)) {
            NameErrorType.INVALID_ERROR
        } else {
            NameErrorType.NONE
        }
    }

    fun setProfile(uri: Uri?, file: File?, type: ProfileImageType) {
        _profileUri.value = uri
        profile.value = file
        profileImageType = type
    }

    fun requestLogin() = viewModelScope.launch {
        loginUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.status == NetworkConstants.SUCCESS_CODE) {
                        saveRefreshToken(result.data.data.refreshToken)
                        saveAccessToken(result.data.data.accessToken)
                        _authState.value = AuthState.SUCCESS_LOGIN
                    }
                }

                is Resource.Loading -> {}

                is Resource.Failure.Error -> {
                    if (result.code == NetworkConstants.USER_NOT_FOUND) {
                        _authState.value = AuthState.NEED_JOIN
                    } else {
                        Log.e(TAG, "requestLogin: ${result.code} ${result.message}")
                    }
                }

                is Resource.Failure.Exception -> {
                    Log.e(TAG, "requestLogin: ${result.message}")
                }
            }
        }
    }

    fun requestJoin() = viewModelScope.launch {
        joinUseCase(name.value, profile.value, profileImageType).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data.status == NetworkConstants.SUCCESS_JOIN_CODE) {
                        saveRefreshToken(result.data.data.refreshToken)
                        saveAccessToken(result.data.data.accessToken)
                        _authState.value = AuthState.SUCCESS_JOIN
                    }
                }

                is Resource.Loading -> {}

                is Resource.Failure.Error -> {
                    Log.e(TAG, "requestJoin: ${result.code} ${result.message}")
                }

                is Resource.Failure.Exception -> {
                    Log.e(TAG, "requestJoin: ${result.message}")
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

    fun saveIdToken(token: String) {
        viewModelScope.launch {
            saveIdTokenUseCase(token)
        }
    }

    fun setAuthState(authState: AuthState) {
        _authState.value = authState
    }

    companion object {
        private const val TAG = "AuthViewModel"
        private const val NICKNAME_MIN_RANGE = 2
        private const val NICKNAME_MAX_RANGE = 10
    }
}