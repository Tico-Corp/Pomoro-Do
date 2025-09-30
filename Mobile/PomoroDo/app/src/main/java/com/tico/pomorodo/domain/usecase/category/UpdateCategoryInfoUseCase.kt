package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateCategoryInfoUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = withContext(Dispatchers.IO) {
        categoryRepository.updateCategory(category)
    }
}