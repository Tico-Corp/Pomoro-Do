package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.local.entity.UserEntity
import com.tico.pomorodo.data.model.CalendarFocusState
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.CategoryWithTodoItem
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.model.Follow
import kotlinx.datetime.LocalDate

object DataSource {
    val userList = listOf<User>(
        User(id = 1, name = "사용자 1"),
        User(id = 2, name = "사용자 2"),
        User(id = 3, name = "사용자 3"),
        User(id = 4, name = "구름이2"),
        User(id = 5, name = "구름이3"),
        User(id = 6, name = "구름이4"),
        User(id = 7, name = "모카커피1"),
        User(id = 8, name = "모카커피2"),
        User(id = 9, name = "모카커피3"),
        User(id = 10, name = "모카커피4"),
        User(id = 11, name = "모카커피5"),
        User(id = 12, name = "a"),
        User(id = 13, name = "b"),
        User(id = 14, name = "차돌1"),
        User(id = 15, name = "차돌2"),
        User(id = 16, name = "구름이1"),
    )

    val inviteList = listOf<CategoryInvitation>(
        CategoryInvitation(
            categoryInvitationId = 1,
            categoryName = "알고리즘 뿌셔!",
            ownerNickname = "랑드샤 쿠키"
        ),
        CategoryInvitation(
            categoryInvitationId = 2,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
        CategoryInvitation(
            categoryInvitationId = 3,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피2"
        ),
        CategoryInvitation(
            categoryInvitationId = 4,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피3"
        ),
        CategoryInvitation(
            categoryInvitationId = 5,
            categoryName = "알고리즘 뿌셔!",
            ownerNickname = "모카커피4"
        ),
        CategoryInvitation(
            categoryInvitationId = 6,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
        CategoryInvitation(
            categoryInvitationId = 7,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
        CategoryInvitation(
            categoryInvitationId = 8,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
        CategoryInvitation(
            categoryInvitationId = 9,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
        CategoryInvitation(
            categoryInvitationId = 10,
            categoryName = "공작 영애들의 우아한 코딩 궁전",
            ownerNickname = "모카커피1"
        ),
    )

    val followList = listOf(
        Follow(followId = 1, name = "사용자 1", isFollowing = true),
        Follow(followId = 2, name = "사용자 2", isFollowing = true),
        Follow(followId = 3, name = "사용자 3", isFollowing = true),
        Follow(followId = 4, name = "구름이2", isFollowing = true),
        Follow(followId = 5, name = "구름이3", isFollowing = true),
        Follow(followId = 6, name = "구름이4", isFollowing = true),
        Follow(followId = 7, name = "모카커피1", isFollowing = true),
        Follow(followId = 8, name = "모카커피2", isFollowing = true),
        Follow(followId = 9, name = "모카커피3", isFollowing = true),
        Follow(followId = 10, name = "모카커피4", isFollowing = true),
        Follow(followId = 11, name = "모카커피5", isFollowing = true),
        Follow(followId = 12, name = "a", isFollowing = true),
        Follow(followId = 13, name = "b", isFollowing = true),
        Follow(followId = 14, name = "차돌1", isFollowing = true),
        Follow(followId = 15, name = "차돌2", isFollowing = true),
        Follow(followId = 16, name = "구름이1", isFollowing = true),
    )

    val INITIAL_TODO_DATA = listOf(
        TodoEntity(
            id = 1,
            title = "Todo 1",
            categoryId = 1,
            status = TodoState.UNCHECKED,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2,
        ),
        TodoEntity(
            id = 2,
            title = "Todo 2",
            categoryId = 1,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 3,
            title = "Todo 3",
            categoryId = 2,
            status = TodoState.UNCHECKED,
            targetDate = LocalDate(2025, 8, 27)
        ),
        TodoEntity(
            id = 4,
            title = "Todo 4",
            categoryId = 3,
            status = TodoState.CHECKED,
            targetDate = LocalDate(2025, 8, 27)
        ),
        TodoEntity(
            id = 5,
            title = "Todo 5",
            categoryId = 4,
            status = TodoState.CHECKED,
            targetDate = LocalDate(2025, 8, 27)
        ),
        TodoEntity(
            id = 6,
            title = "Todo 6",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 7,
            title = "Todo 7",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 8,
            title = "Todo 8",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 9,
            title = "Todo 9",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 10,
            title = "Todo 10",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        ),
        TodoEntity(
            id = 11,
            title = "Todo 11",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2025, 8, 27),
            likes = 2
        )
    )

    val INITIAL_CATEGORY_DATA = listOf(
        CategoryEntity(
            id = 1,
            type = CategoryType.GROUP,
            openSettings = OpenSettings.GROUP,
            title = "그룹 카테고리 1",
            ownerFlag = true,
            ownerNickname = "사용자 1",
            groupMember = listOf(
                UserEntity(id = 1, name = "사용자 1"),
                UserEntity(id = 2, name = "사용자 2"),
                UserEntity(id = 3, name = "사용자 3"),
                UserEntity(id = 4, name = "구름이2"),
                UserEntity(id = 5, name = "구름이3"),
                UserEntity(id = 6, name = "구름이4"),
            ),
            totalMembers = 6
        ),
        CategoryEntity(
            id = 2,
            type = CategoryType.GROUP,
            openSettings = OpenSettings.GROUP,
            title = "그룹 카테고리 2",
            ownerFlag = false,
            ownerNickname = "사용자 2",
            groupMember = listOf(
                UserEntity(id = 7, name = "모카커피1"),
                UserEntity(id = 8, name = "모카커피2"),
                UserEntity(id = 9, name = "모카커피3"),
                UserEntity(id = 10, name = "모카커피4"),
                UserEntity(id = 11, name = "모카커피5"),
            ),
            totalMembers = 5
        ),
        CategoryEntity(
            id = 3,
            type = CategoryType.PERSONAL,
            openSettings = OpenSettings.PRIVATE,
            title = "개인 카테고리 1",
            ownerFlag = true,
            ownerNickname = "사용자 1",
            groupMember = null,
            totalMembers = 0
        ),
        CategoryEntity(
            id = 4,
            type = CategoryType.PERSONAL,
            openSettings = OpenSettings.FOLLOWERS,
            title = "개인 카테고리 2",
            ownerFlag = true,
            ownerNickname = "사용자 1",
            groupMember = null,
            totalMembers = 0
        ),
    )

    val INITIAL_CALENDAR_DATA = CalendarDateEntity(
        date = LocalDate(2024, 12, 5),
        focusState = CalendarFocusState.WHITE,
        totalCount = 6,
        remainedTodoCount = 4
    )

    val categoryWithTodoItemList =
        listOf(
            CategoryWithTodoItem(
                categoryId = 1,
                title = "그룹 카테고리 1",
                type = CategoryType.GROUP,
                todoList = listOf(
                    TodoData(
                        id = 1,
                        title = "Group Todo 1",
                        status = TodoState.UNCHECKED,
                        categoryId = 1,
                        completedList = listOf(
                            User(id = 1, name = "사용자 1"),
                            User(id = 2, name = "사용자 2"),
                            User(id = 3, name = "사용자 3")
                        ),
                        incompletedList = listOf(
                            User(id = 4, name = "구름이2"),
                            User(id = 5, name = "구름이3"),
                            User(id = 6, name = "구름이4"),
                        ),
                        likes = 2,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 1,
                        title = "Group Todo 2",
                        status = TodoState.UNCHECKED,
                        categoryId = 1,
                        completedList = listOf(
                            User(id = 1, name = "사용자 1"),
                            User(id = 2, name = "사용자 2"),
                            User(id = 3, name = "사용자 3"),
                            User(id = 4, name = "구름이2"),
                            User(id = 5, name = "구름이3"),
                        ),
                        incompletedList = listOf(
                            User(id = 6, name = "구름이4"),
                        ),
                        likes = 0,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                ),
                openSettings = OpenSettings.GROUP,
                groupMembers = listOf(
                    User(id = 1, name = "사용자 1"),
                    User(id = 2, name = "사용자 2"),
                    User(id = 3, name = "사용자 3"),
                    User(id = 4, name = "구름이2"),
                    User(id = 5, name = "구름이3"),
                    User(id = 6, name = "구름이4"),
                ),
                totalMembers = 6
            ),
            CategoryWithTodoItem(
                categoryId = 2,
                title = "그룹 카테고리 2",
                type = CategoryType.GROUP,
                todoList = listOf(
                    TodoData(
                        id = 3,
                        title = "Group Todo 1",
                        status = TodoState.UNCHECKED,
                        categoryId = 2,
                        completedList = listOf(),
                        incompletedList = listOf(
                            User(id = 7, name = "모카커피1"),
                            User(id = 8, name = "모카커피2"),
                            User(id = 9, name = "모카커피3"),
                            User(id = 10, name = "모카커피4"),
                            User(id = 11, name = "모카커피5"),
                        ),
                        likes = 2,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 4,
                        title = "Group Todo 2",
                        status = TodoState.UNCHECKED,
                        categoryId = 2,
                        completedList = listOf(
                            User(id = 7, name = "모카커피1"),
                            User(id = 8, name = "모카커피2"),
                            User(id = 9, name = "모카커피3"),
                            User(id = 10, name = "모카커피4"),
                            User(id = 11, name = "모카커피5"),
                        ),
                        incompletedList = listOf(),
                        likes = 0,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                ),
                openSettings = OpenSettings.GROUP,
                groupMembers = listOf(
                    User(id = 7, name = "모카커피1"),
                    User(id = 8, name = "모카커피2"),
                    User(id = 9, name = "모카커피3"),
                    User(id = 10, name = "모카커피4"),
                    User(id = 11, name = "모카커피5"),
                ),
                totalMembers = 6
            ),
            CategoryWithTodoItem(
                categoryId = 3,
                title = "일반 카테고리 1",
                type = CategoryType.PERSONAL,
                todoList = listOf(
                    TodoData(
                        id = 5,
                        title = "Personal Todo 1",
                        status = TodoState.UNCHECKED,
                        categoryId = 3,
                        likes = 2,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 6,
                        title = "Personal Todo 2",
                        status = TodoState.UNCHECKED,
                        categoryId = 3,
                        likes = 0,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 7,
                        title = "Personal Todo 3",
                        status = TodoState.UNCHECKED,
                        categoryId = 3,
                        likes = 5,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                ),
                totalMembers = 0
            ),
            CategoryWithTodoItem(
                categoryId = 4,
                title = "일반 카테고리 2",
                type = CategoryType.PERSONAL,
                todoList = listOf(
                    TodoData(
                        id = 8,
                        title = "Personal Todo 1",
                        status = TodoState.UNCHECKED,
                        categoryId = 4,
                        likes = 2,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 9,
                        title = "Personal Todo 2",
                        status = TodoState.UNCHECKED,
                        categoryId = 4,
                        likes = 0,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                    TodoData(
                        id = 10,
                        title = "Personal Todo 3",
                        status = TodoState.UNCHECKED,
                        categoryId = 4,
                        likes = 5,
                        targetDate = LocalDate(2025, 8, 27),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    ),
                ),
                totalMembers = 0
            ),
        )
}