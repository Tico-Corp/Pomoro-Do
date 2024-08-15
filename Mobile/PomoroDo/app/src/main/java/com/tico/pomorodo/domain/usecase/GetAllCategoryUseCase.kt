package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Category>>> = withContext(Dispatchers.IO) {
        categoryRepository.getAllCategory()
    }
}