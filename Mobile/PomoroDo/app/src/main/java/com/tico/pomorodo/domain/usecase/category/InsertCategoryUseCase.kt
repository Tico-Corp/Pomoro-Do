package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(
        title: String,
        type: CategoryType,
        isGroupReader: Boolean,
        openSettings: OpenSettings,
        groupReader: String,
        groupMemberCount: Int,
        groupMember: List<User>
    ) = withContext(Dispatchers.IO) {
        categoryRepository.insert(
            title = title,
            type = type,
            isGroupReader = isGroupReader,
            openSettings = openSettings,
            groupReader = groupReader,
            groupMemberCount = groupMemberCount,
            groupMember = groupMember
        )
    }
}