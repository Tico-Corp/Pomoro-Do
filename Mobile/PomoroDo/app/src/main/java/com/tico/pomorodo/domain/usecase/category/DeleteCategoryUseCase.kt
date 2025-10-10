package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryId: Int, deleteOption: DeletionOption) =
        categoryRepository.deleteCategory(categoryId, deleteOption)
}