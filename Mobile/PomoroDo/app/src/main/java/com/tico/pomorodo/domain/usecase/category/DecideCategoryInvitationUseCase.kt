package com.tico.pomorodo.domain.usecase.category

import com.tico.pomorodo.data.model.Decision
import com.tico.pomorodo.domain.repository.CategoryRepository
import javax.inject.Inject

class DecideCategoryInvitationUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(invitationId: Int, decision: Decision) =
        categoryRepository.decideCategoryInvitation(invitationId, decision)
}