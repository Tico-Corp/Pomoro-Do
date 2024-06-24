package com.tico.pomorodo.ui.category.viewModel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.local.entity.OpenSettings
import com.tico.pomorodo.data.local.entity.SelectedUser
import com.tico.pomorodo.data.local.entity.User
import com.tico.pomorodo.ui.common.view.toUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel : ViewModel() {

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

    fun setOpenSettingOption(newOption: OpenSettings) {
        _openSettingOption.value = newOption
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setType(type: Boolean) {
        _type.value = type
    }

    fun setGroupMembers(newList: List<SelectedUser>) {
        _groupMembers.value =
            newList.toMutableList().filter { it.selected }.map { it.toUser() }
    }

    fun validateInput(): Boolean = title.value.isNotBlank()
}