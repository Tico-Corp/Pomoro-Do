package com.tico.pomorodo.ui.member.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource.followList
import com.tico.pomorodo.domain.model.Follow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor() : ViewModel() {
    private val _followingList: MutableStateFlow<List<Follow>> = MutableStateFlow(followList)
    val followingList: StateFlow<List<Follow>> = _followingList

    private val _followerList: MutableStateFlow<List<Follow>> = MutableStateFlow(followList)
    val followerList: StateFlow<List<Follow>> = _followerList

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
}