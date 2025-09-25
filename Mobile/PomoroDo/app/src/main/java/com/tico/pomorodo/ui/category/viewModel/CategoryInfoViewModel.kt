package com.tico.pomorodo.ui.category.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.data.model.toSelectedUser
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.category.GetCategoryInfoUseCase
import com.tico.pomorodo.domain.usecase.category.UpdateCategoryInfoUseCase
import com.tico.pomorodo.navigation.CategoryArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCategoryInfoUseCase: GetCategoryInfoUseCase,
    private val updateCategoryInfoUseCase: UpdateCategoryInfoUseCase
) : ViewModel() {

    private val args = CategoryArgs(savedStateHandle)

    private var _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?>
        get() = _category.asStateFlow()

    private var _selectedGroupMembers =
        MutableStateFlow<List<SelectedUser>>(listOf())
    val selectedGroupMembers: StateFlow<List<SelectedUser>>
        get() = _selectedGroupMembers.asStateFlow()

    private val follower = MutableStateFlow<List<User>?>(null)

    init {
        loadData()
    }

    private fun loadData() {
        fetchCategoryInfo(args.categoryId)
        fetchFollower()

        viewModelScope.launch {
            combine(category, follower) { category, follower ->
                category != null && follower != null
            }.collect { isDataLoaded ->
                if (isDataLoaded) {
                    fetchSelectedGroupMembers()
                }
            }
        }
    }

    fun setOpenSettingOption(newOption: OpenSettings) {
        _category.value = category.value?.copy(openSettings = newOption)
    }

    fun setTitle(title: String) {
        _category.value = category.value?.copy(title = title)
    }

    fun setSelectedGroupMembers(newList: List<SelectedUser>) {
        _selectedGroupMembers.value = newList
    }

    fun validateInput(): Boolean {
        category.value?.let { category ->
            if (category.type == CategoryType.PERSONAL) {
                return category.title.isNotBlank()
            } else {
                return category.title.isNotBlank() && selectedGroupMembers.value.any { it.selected }
            }
        }
        return false
    }

    private fun fetchCategoryInfo(categoryId: Int) = viewModelScope.launch {
        getCategoryInfoUseCase(categoryId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _category.value = result.data
                }

                is Resource.Loading -> {
                }

                is Resource.Failure.Exception -> {
                    Log.e("CategoryInfoViewModel", "fetchCategoryInfo: ${result.message}")
                }

                is Resource.Failure.Error -> {
                    Log.e(
                        "CategoryInfoViewModel",
                        "fetchCategoryInfo: ${result.code} ${result.message}"
                    )
                }
            }
        }
    }

    private fun fetchFollower() {
        follower.value = DataSource.userList
    }

    private fun fetchSelectedGroupMembers() {
        val categoryValue = category.value
        val followerValue = follower.value

        if (categoryValue != null && followerValue != null) {
            val groupMembers = categoryValue.groupMembers

            if (groupMembers != null) {
                _selectedGroupMembers.value = followerValue.map { user ->
                    val isSelected = groupMembers.any { it.id == user.id }
                    user.toSelectedUser(selected = isSelected)
                }
            }
        }
    }

    fun updateCategoryInfo() {
        viewModelScope.launch {
            category.value?.let {
                updateCategoryInfoUseCase(it)
            }
        }
    }
}