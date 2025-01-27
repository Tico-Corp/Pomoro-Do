package com.tico.pomorodo.ui.category.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.toUser
import com.tico.pomorodo.domain.usecase.category.InsertCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(private val insertCategoryUseCase: InsertCategoryUseCase) :
    ViewModel() {

    private var _title = MutableStateFlow("")
    val title: StateFlow<String>
        get() = _title.asStateFlow()

    private var _type = MutableStateFlow(CategoryType.GENERAL)
    val type: StateFlow<CategoryType>
        get() = _type.asStateFlow()

    private var _openSettingOption = MutableStateFlow(OpenSettings.FULL)
    val openSettingOption: StateFlow<OpenSettings>
        get() = _openSettingOption.asStateFlow()

    private var _selectedGroupMembers =
        MutableStateFlow<List<SelectedUser>>(emptyList())
    val selectedGroupMembers: StateFlow<List<SelectedUser>>
        get() = _selectedGroupMembers.asStateFlow()

    init {
        fetchSelectedGroupMembers()
    }

    fun setOpenSettingOption(newOption: OpenSettings) {
        _openSettingOption.value = newOption
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setType(type: CategoryType) {
        _type.value = type
    }

    fun setSelectedGroupMembers(newList: List<SelectedUser>) {
        _selectedGroupMembers.value = newList
    }

    fun validateInput(): Boolean =
        if (type.value == CategoryType.GENERAL) title.value.isNotBlank() else title.value.isNotBlank() && selectedGroupMembers.value.any { it.selected }

    private fun fetchSelectedGroupMembers() {
        // TODO: 선택된 그룹원 불러오는 로직
    }

    fun insertCategory() = viewModelScope.launch {
        val groupMember = selectedGroupMembers.value.filter { it.selected }.map { it.toUser() }
        insertCategoryUseCase.invoke(
            title = title.value,
            type = type.value,
            isGroupReader = true,
            openSettings = openSettingOption.value,
            groupReader = "",
            groupMemberCount = groupMember.size,
            groupMember = groupMember
        )
    }
}