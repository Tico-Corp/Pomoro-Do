package com.tico.pomorodo.ui.member.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.common.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(networkHelper: NetworkHelper) : ViewModel() {
    private var _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private var _profile = MutableStateFlow<String?>(null)
    val profile: StateFlow<String?> = _profile.asStateFlow()
}