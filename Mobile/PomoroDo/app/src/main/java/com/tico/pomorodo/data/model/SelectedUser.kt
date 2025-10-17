package com.tico.pomorodo.data.model

data class SelectedUser(
    val id: Int,
    val name: String,
    val profileUrl: String? = null,
    val selected: Boolean
)

fun User.toSelectedUser(selected: Boolean = false): SelectedUser =
    SelectedUser(
        id = this.id,
        name = this.name,
        profileUrl = this.profileUrl,
        selected = selected
    )

fun SelectedUser.toUser(): User =
    User(id = this.id, name = this.name, profileUrl = this.profileUrl)