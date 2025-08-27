package com.tico.pomorodo.ui.member.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.UserProfile
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase) :
    ViewModel() {
    private val _userProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        getUserProfile()
    }

    private fun getUserProfile() = viewModelScope.launch {
        getUserProfileUseCase().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    /*TODO: 로딩화면 구현*/
                }

                is Resource.Success -> {
                    _userProfile.value = result.data
                }

                is Resource.Failure.Error ->
                    Log.e("MyPageViewModel", "getUserProfile: ${result.message}")

                is Resource.Failure.Exception ->
                    Log.e("MyPageViewModel", "getUserProfile: ${result.code} ${result.message}")
            }
        }
    }
}