package com.tico.pomorodo.domain.usecase.todo

import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetCategoryWithTodoItemsUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(selectedDate: LocalDate) =
        withContext(Dispatchers.IO) {
            todoRepository.getCategoryWithTodoItems(selectedDate)
        }
}