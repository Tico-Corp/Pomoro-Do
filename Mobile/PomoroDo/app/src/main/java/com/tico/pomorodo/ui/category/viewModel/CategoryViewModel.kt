package com.tico.pomorodo.ui.category.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.GroupCategory
import com.tico.pomorodo.data.model.PersonalCategory
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.category.GetAllCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val getAllCategoryUseCase: GetAllCategoryUseCase) :
    ViewModel() {
    private var _personalCategories = MutableStateFlow<List<PersonalCategory>>(emptyList())
    val personalCategories: StateFlow<List<PersonalCategory>>
        get() = _personalCategories.asStateFlow()

    private var _groupCategories = MutableStateFlow<List<GroupCategory>>(emptyList())
    val groupCategories: StateFlow<List<GroupCategory>>
        get() = _groupCategories.asStateFlow()

    private var _categoryInvitations = MutableStateFlow<List<CategoryInvitation>>(emptyList())
    val categoryInvitations: StateFlow<List<CategoryInvitation>>
        get() = _categoryInvitations.asStateFlow()

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