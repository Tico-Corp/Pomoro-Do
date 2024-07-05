package com.tico.pomorodo.ui.auth.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.domain.usecase.ClearTokenUseCase
import com.tico.pomorodo.domain.usecase.GetTokenUseCase
import com.tico.pomorodo.domain.usecase.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {

    private var _name = MutableStateFlow<String>("")
    val name: StateFlow<String>
        get() = _name.asStateFlow()

    private var _profile = MutableStateFlow<Uri?>(null)
    val profile: StateFlow<Uri?>
        get() = _profile.asStateFlow()

    private var authToken = MutableStateFlow<String?>(null)

    private var googleIdToken = MutableStateFlow<String?>(null)
    fun setName(inputText: String) {
        _name.value = inputText
    }

    fun setProfile(url: Uri?) {
        _profile.value = url
    }

    fun saveIdToken(idToken: String) {
        googleIdToken.value = idToken
    }

    fun requestLogin() {
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            saveTokenUseCase(token)
        }
    }

    fun getToken() {
        viewModelScope.launch {
            authToken.value = getTokenUseCase()
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            clearTokenUseCase()
            authToken.value = null
        }
    }
}