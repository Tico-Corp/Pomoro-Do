package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.model.InviteCategory
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.model.Follow

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
}