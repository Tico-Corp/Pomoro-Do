package com.tico.pomorodo.ui.category.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.navigation.CategoryArgs
import com.tico.pomorodo.ui.common.view.toSelectedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InfoCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
class CategoryInfoViewModel @Inject constructor(
) : ViewModel() {

    private val args = CategoryArgs(savedStateHandle)

    private var _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?>
        get() = _category.asStateFlow()

    private var _selectedGroupMembers =
        MutableStateFlow<List<SelectedUser>>(listOf())
    val selectedGroupMembers: StateFlow<List<SelectedUser>>
        get() = _selectedGroupMembers.asStateFlow()

    private var follower = MutableStateFlow<List<User>?>(null)

    init {
        fetchCategoryInfo(args.categoryId)
        fetchFollower()
        fetchSelectedGroupMembers()
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
            if (category.type == CategoryType.GENERAL) {
                return category.title.isNotBlank()
            } else {
                return category.title.isNotBlank() && selectedGroupMembers.value.any { it.selected }
            }
        }
        return false
    }

    private fun fetchCategoryInfo(categoryId: Int) {
        TODO("category 목록에서 categoryId로 조회")
    }

    private fun fetchFollower() {
        follower.value = DataSource.userList
    }

    private fun fetchSelectedGroupMembers() {
        val categoryValue = category.value
        val followerValue = follower.value

        if (categoryValue != null && followerValue != null) {
            val groupMembers = categoryValue.groupMember

            if (groupMembers != null) {
                _selectedGroupMembers.value = followerValue.map { user ->
                    val isSelected = groupMembers.any { it.id == user.id }
                    user.toSelectedUser(selected = isSelected)
                }
            }
        }
    }
}