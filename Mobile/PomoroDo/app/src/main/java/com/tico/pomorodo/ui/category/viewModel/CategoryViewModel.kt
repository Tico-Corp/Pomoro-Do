package com.tico.pomorodo.ui.category.viewModel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.InviteCategory
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
}