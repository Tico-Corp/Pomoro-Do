package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.entity.Category
import com.tico.pomorodo.data.local.entity.InviteCategory
import com.tico.pomorodo.data.local.entity.TodoData
import com.tico.pomorodo.data.local.entity.User
import com.tico.pomorodo.ui.todo.view.TodoState

object DataSource {
    val userList = listOf<User>(
        User(id = "1", name = "차돌1"),
        User(id = "2", name = "차돌2"),
        User(id = "3", name = "구름이1"),
        User(id = "4", name = "구름이2"),
        User(id = "5", name = "구름이3"),
        User(id = "6", name = "구름이4"),
        User(id = "7", name = "모카커피1"),
        User(id = "8", name = "모카커피2"),
        User(id = "9", name = "모카커피3"),
        User(id = "10", name = "모카커피4"),
        User(id = "11", name = "모카커피5"),
        User(id = "12", name = "a"),
        User(id = "13", name = "b"),
    )

    val todoList = listOf<Category>(
        Category(
            id = "1",
            title = "카테고리 1",
            groupNumber = 5,
            todoList = listOf(
                TodoData(
                    id = "1",
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
                TodoData(
                    id = "2",
                    name = "Todo 1",
                    state = TodoState.CHECKED,
                    completeGroupNumber = 1,
                    likedNumber = 22
                ),
                TodoData(
                    id = "3",
                    name = "Todo 1",
                    state = TodoState.GOING,
                    completeGroupNumber = 3,
                    likedNumber = 22
                ),
                TodoData(
                    id = "4",
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 0,
                    likedNumber = 0
                )
            )
        ),
        Category(
            id = "2",
            title = "카테고리 2",
            todoList = listOf(
                TodoData(
                    id = "1",
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
            )
        ),
        Category(
            id = "3",
            title = "카테고리 3",
            todoList = listOf(
                TodoData(
                    id = "1",
                    name = "Todo 1",
                    state = TodoState.UNCHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
                TodoData(
                    id = "2",
                    name = "Todo 1",
                    state = TodoState.CHECKED,
                    completeGroupNumber = 4,
                    likedNumber = 22
                ),
            )
        )
    )

    val inviteList = listOf<InviteCategory>(
        InviteCategory(id = "1", title = "알고리즘 뿌셔!", groupReader ="랑드샤 쿠키"),
        InviteCategory(id = "2", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
        InviteCategory(id = "3", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피2"),
        InviteCategory(id = "4", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피3"),
        InviteCategory(id = "5", title = "알고리즘 뿌셔!", groupReader ="모카커피4"),
        InviteCategory(id = "6", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader ="모카커피1"),
    )
}