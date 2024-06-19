package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.ui.todo.view.Category
import com.tico.pomorodo.ui.todo.view.TodoData
import com.tico.pomorodo.ui.todo.view.TodoState
import com.tico.pomorodo.ui.todo.view.User

object DataSource {
    val userList = listOf<User>(
        User(name = "차돌"),
        User(name = "차돌"),
        User(name = "구름이"),
        User(name = "구름이"),
        User(name = "구름이"),
        User(name = "구름이"),
        User(name = "모카커피")
    )

    val todoList = listOf<Category>(
        Category(
            title = "카테고리 1",
            groupNumber = 5,
            todoList = listOf(
                TodoData(
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
                TodoData(
                    name = "Todo 1",
                    state = TodoState.CHECKED,
                    completeGroupNumber = 1,
                    likedNumber = 22
                ),
                TodoData(
                    name = "Todo 1",
                    state = TodoState.GOING,
                    completeGroupNumber = 3,
                    likedNumber = 22
                ),
                TodoData(
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 0,
                    likedNumber = 0
                )
            )
        ),
        Category(
            title = "카테고리 2",
            todoList = listOf(
                TodoData(
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
            )
        ), Category(
            title = "카테고리 3",
            todoList = listOf(
                TodoData(
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
                TodoData(
                    name = "Todo 1",
                    state = TodoState.CHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
            )
        )
    )
}