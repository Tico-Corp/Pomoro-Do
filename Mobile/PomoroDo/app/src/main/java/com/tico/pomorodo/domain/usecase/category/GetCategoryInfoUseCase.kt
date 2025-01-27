package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoryInfoUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryId: Int) = withContext(Dispatchers.IO) {
        categoryRepository.getCategoryInfo(categoryId)
    }
}