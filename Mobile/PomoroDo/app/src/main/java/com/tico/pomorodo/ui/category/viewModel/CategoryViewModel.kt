package com.tico.pomorodo.ui.category.viewModel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.InviteCategory
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.ui.common.view.toSelectedUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel : ViewModel() {

    private var _categoryList = MutableStateFlow(DataSource.categoryList)
    val categoryList: StateFlow<List<Category>>
        get() = _categoryList.asStateFlow()

    private var _inviteGroupCategoryList = MutableStateFlow(DataSource.inviteList)
    val inviteGroupCategoryList: StateFlow<List<InviteCategory>>
        get() = _inviteGroupCategoryList.asStateFlow()

    private var _openSettingOption = MutableStateFlow(OpenSettings.FULL)
    val openSettingOption: StateFlow<OpenSettings>
        get() = _openSettingOption.asStateFlow()

    private var _title = MutableStateFlow("")
    val title: StateFlow<String>
        get() = _title.asStateFlow()

    private var _type = MutableStateFlow(false)
    val type: StateFlow<Boolean>
        get() = _type.asStateFlow()

    private var _groupMembers = MutableStateFlow<List<User>>(DataSource.userList)
    val groupMembers: StateFlow<List<User>>
        get() = _groupMembers.asStateFlow()

    private var _selectedGroupMembers =
        MutableStateFlow<List<SelectedUser>>(groupMembers.value.map { it.toSelectedUser() })
    val selectedGroupMembers: StateFlow<List<SelectedUser>>
        get() = _selectedGroupMembers.asStateFlow()

    fun setOpenSettingOption(newOption: OpenSettings) {
        _openSettingOption.value = newOption
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setType(type: Boolean) {
        _type.value = type
    }

    fun setSelectedGroupMembers(newList: List<SelectedUser>) {
        _selectedGroupMembers.value = newList
    }

    fun validateInput(): Boolean = title.value.isNotBlank()
}