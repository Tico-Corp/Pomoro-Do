package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(
        title: String,
        type: CategoryType,
        openSettings: OpenSettings,
        groupMemberIds: List<Int>?,
    ) = withContext(Dispatchers.IO) {
        val startDate =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        categoryRepository.insertCategory(
            title = title,
            type = type,
            openSettings = openSettings,
            groupMemberIds = groupMemberIds,
            startDate = startDate
        )
    }
}