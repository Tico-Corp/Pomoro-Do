package com.tico.pomorodo.ui.follow.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.follow.FindUserByNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(private val findUserByNicknameUseCase: FindUserByNicknameUseCase) :
    ViewModel() {
    private val _followingList: MutableStateFlow<List<Follow>> =
        MutableStateFlow(DataSource.followList)
    val followingList: StateFlow<List<Follow>> = _followingList.asStateFlow()

    private val _followerList: MutableStateFlow<List<Follow>> =
        MutableStateFlow(DataSource.followList)
    val followerList: StateFlow<List<Follow>> = _followerList.asStateFlow()

    private val _findList: MutableStateFlow<List<Follow>> = MutableStateFlow(listOf())
    val findList: StateFlow<List<Follow>> = _findList.asStateFlow()

    fun toggleFollowState(index: Int) {
        val list = _followingList.value.toMutableList()
        list[index] = list[index].copy(isFollowing = !list[index].isFollowing)

        _followingList.value = list.toList()
    }

    fun removeFollower(index: Int) {
        val list = _followerList.value.toMutableList()
        list.removeAt(index)

        _followerList.value = list.toList()
    }

    fun findUserByNickname(nickname: String) = viewModelScope.launch {
        findUserByNicknameUseCase(nickname).collect { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _findList.value = result.data
                }

                is Resource.Failure.Error -> {
                    Log.e(
                        "FollowViewModel",
                        "findUserByNickname: [${result.code}] ${result.message}"
                    )
                }

                is Resource.Failure.Exception -> {
                    Log.e("FollowViewModel", "findUserByNickname: ${result.message}")
                }
            }
        }
    }
}