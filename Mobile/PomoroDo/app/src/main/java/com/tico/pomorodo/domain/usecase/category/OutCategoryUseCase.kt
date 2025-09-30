package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.domain.repository.CategoryRepository
import javax.inject.Inject

class OutCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryId: Int, deleteOption: DeletionOption) =
        categoryRepository.outCategory(categoryId, deleteOption)
}