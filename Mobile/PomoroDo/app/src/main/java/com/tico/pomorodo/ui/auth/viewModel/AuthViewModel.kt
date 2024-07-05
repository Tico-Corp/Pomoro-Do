package com.tico.pomorodo.ui.auth.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
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

    fun requestLogin() {

    }
}