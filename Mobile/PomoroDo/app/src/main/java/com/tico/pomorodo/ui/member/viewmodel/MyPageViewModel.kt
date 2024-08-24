package com.tico.pomorodo.ui.member.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {
    private var _name = MutableStateFlow("모카커피짱귀엽")
    val name: StateFlow<String> = _name.asStateFlow()

    private var _profile = MutableStateFlow<Uri?>(null)
    val profile: StateFlow<Uri?> = _profile.asStateFlow()
}