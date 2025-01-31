package com.tico.pomorodo.domain.usecase.todo

import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(
        title: String,
        categoryId: Int,
        completedList: List<User>?,
        incompletedList: List<User>?,
        targetDate: LocalDate
    ) = withContext(Dispatchers.IO) {
        todoRepository.insertTodo(
            title,
            categoryId,
            completedList,
            incompletedList,
            targetDate
        )
    }
}