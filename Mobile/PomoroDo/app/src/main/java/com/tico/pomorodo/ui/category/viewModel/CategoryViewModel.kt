package com.tico.pomorodo.ui.category.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.CategoryList
import com.tico.pomorodo.data.model.Decision
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.category.DecideCategoryInvitationUseCase
import com.tico.pomorodo.domain.usecase.category.GetAllCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val decideCategoryInvitationUseCase: DecideCategoryInvitationUseCase
) :
    ViewModel() {
    private var _personalCategories = MutableStateFlow<List<CategoryList>>(emptyList())
    val personalCategories: StateFlow<List<CategoryList>>
        get() = _personalCategories.asStateFlow()

    private var _groupCategories = MutableStateFlow<List<CategoryList>>(emptyList())
    val groupCategories: StateFlow<List<CategoryList>>
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
                    _personalCategories.value = result.data.personalCategories
                    _groupCategories.value = result.data.groupCategories
                    _categoryInvitations.value = result.data.categoryInvitations
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

    fun decideCategoryInvitation(invitationId: Int, decision: Decision) = viewModelScope.launch {
        when (val result = decideCategoryInvitationUseCase(invitationId, decision)) {
            is Resource.Success -> {
                _categoryInvitations.value =
                    _categoryInvitations.value.filterNot { it.categoryInvitationId == invitationId }
            }

            is Resource.Loading -> {
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