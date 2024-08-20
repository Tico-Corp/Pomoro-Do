package com.tico.pomorodo.ui.category.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.InviteCategory
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.GetAllCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val getAllCategoryUseCase: GetAllCategoryUseCase) :
    ViewModel() {

    private var _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>>
        get() = _categoryList.asStateFlow()

    private var _inviteGroupCategoryList = MutableStateFlow(DataSource.inviteList)
    val inviteGroupCategoryList: StateFlow<List<InviteCategory>>
        get() = _inviteGroupCategoryList.asStateFlow()

    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    init {
        getAllCategory()
    }

    private fun getAllCategory() = viewModelScope.launch {
        getAllCategoryUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _isLoading.value = false
                    _categoryList.value = result.data
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }

                is Resource.Failure.Exception -> {
                    Log.e("CategoryViewModel", "getAllCategory: ${result.message}")
                }

                is Resource.Failure.Error -> {
                    Log.e("CategoryViewModel", "getAllCategory: ${result.code} ${result.message}")
                }
            }
        }
    }
}