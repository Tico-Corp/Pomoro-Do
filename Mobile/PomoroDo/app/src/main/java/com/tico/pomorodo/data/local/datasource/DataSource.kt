package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.entity.CalendarDateEntity
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.TodoEntity
import com.tico.pomorodo.data.local.entity.UserEntity
import com.tico.pomorodo.data.model.CalendarFocusState
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.InviteCategory
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.TodoState
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.model.Follow
import kotlinx.datetime.LocalDate

object DataSource {
    val userList = listOf<User>(
        User(id = 1, name = "사용자 1", email = "abc@abc.abc"),
        User(id = 2, name = "사용자 2", email = "abcd@abc.abc"),
        User(id = 3, name = "사용자 3", email = "abce@abc.abc"),
        User(id = 4, name = "구름이2", email = "abc@gmail.com"),
        User(id = 5, name = "구름이3", email = "abc@gmail.com"),
        User(id = 6, name = "구름이4", email = "abc@gmail.com"),
        User(id = 7, name = "모카커피1", email = "abc@gmail.com"),
        User(id = 8, name = "모카커피2", email = "abc@gmail.com"),
        User(id = 9, name = "모카커피3", email = "abc@gmail.com"),
        User(id = 10, name = "모카커피4", email = "abc@gmail.com"),
        User(id = 11, name = "모카커피5", email = "abc@gmail.com"),
        User(id = 12, name = "a", email = "abc@gmail.com"),
        User(id = 13, name = "b", email = "abc@gmail.com"),
        User(id = 14, name = "차돌1", email = "abc@gmail.com"),
        User(id = 15, name = "차돌2", email = "abc@gmail.com"),
        User(id = 16, name = "구름이1", email = "abc@gmail.com"),
    )

    val inviteList = listOf<InviteCategory>(
        InviteCategory(id = "1", title = "알고리즘 뿌셔!", groupReader = "랑드샤 쿠키"),
        InviteCategory(id = "2", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
        InviteCategory(id = "3", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피2"),
        InviteCategory(id = "4", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피3"),
        InviteCategory(id = "5", title = "알고리즘 뿌셔!", groupReader = "모카커피4"),
        InviteCategory(id = "6", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
        InviteCategory(id = "7", title = "공작 영애들의 우아한 코딩 궁전", groupReader = "모카커피1"),
    )

    val followList = listOf(
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
        Follow(followId = 0, name = "모카커피짱귀엽", isFollowing = true),
    )

    val INITIAL_TODO_DATA = listOf(
        TodoEntity(
            id = 1,
            title = "Todo 1",
            categoryId = 1,
            status = TodoState.UNCHECKED,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2,
        ),
        TodoEntity(
            id = 2,
            title = "Todo 2",
            categoryId = 1,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 3,
            title = "Todo 3",
            categoryId = 2,
            status = TodoState.UNCHECKED,
            targetDate = LocalDate(2024, 12, 5)
        ),
        TodoEntity(
            id = 4,
            title = "Todo 4",
            categoryId = 3,
            status = TodoState.CHECKED,
            targetDate = LocalDate(2024, 12, 5)
        ),
        TodoEntity(
            id = 5,
            title = "Todo 5",
            categoryId = 4,
            status = TodoState.CHECKED,
            targetDate = LocalDate(2024, 12, 5)
        ),
        TodoEntity(
            id = 6,
            title = "Todo 6",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 7,
            title = "Todo 7",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 8,
            title = "Todo 8",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 9,
            title = "Todo 9",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 10,
            title = "Todo 10",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        ),
        TodoEntity(
            id = 11,
            title = "Todo 11",
            categoryId = 4,
            status = TodoState.GOING,
            targetDate = LocalDate(2024, 12, 5),
            likes = 2
        )
    )

    val INITIAL_CATEGORY_DATA = listOf(
        CategoryEntity(
            id = 1,
            type = CategoryType.GROUP,
            openSettings = OpenSettings.GROUP,
            title = "그룹 카테고리 1",
            groupMemberCount = 2,
            isGroupReader = true,
            groupReader = "사용자 1",
            groupMember = listOf(
                UserEntity(id = 1, name = "사용자 1", email = "abc@abc.abc"),
                UserEntity(id = 2, name = "사용자 2", email = "abcd@abc.abc"),
                UserEntity(id = 3, name = "사용자 3", email = "abce@abc.abc")
            ),
        ),
        CategoryEntity(
            id = 2,
            type = CategoryType.GROUP,
            openSettings = OpenSettings.GROUP,
            title = "그룹 카테고리 2",
            groupMemberCount = 2,
            isGroupReader = false,
            groupReader = "사용자 2",
            groupMember = listOf(
                UserEntity(id = 1, name = "사용자 1", email = "abc@abc.abc"),
                UserEntity(id = 2, name = "사용자 2", email = "abcd@abc.abc"),
                UserEntity(id = 3, name = "사용자 3", email = "abce@abc.abc")
            ),
        ),
        CategoryEntity(
            id = 3,
            type = CategoryType.GENERAL,
            openSettings = OpenSettings.ME,
            title = "일반 카테고리 1",
            isGroupReader = true,
            groupReader = "사용자 1",
            groupMember = null
        ),
        CategoryEntity(
            id = 4,
            type = CategoryType.GENERAL,
            openSettings = OpenSettings.FOLLOWER,
            title = "일반 카테고리 2",
            isGroupReader = true,
            groupReader = "사용자 1",
            groupMember = null
        ),
    )

    val INITIAL_CALENDAR_DATA = CalendarDateEntity(
        date = LocalDate(2024, 12, 5),
        focusState = CalendarFocusState.WHITE,
        totalCount = 6,
        remainedTodoCount = 4
    )
}