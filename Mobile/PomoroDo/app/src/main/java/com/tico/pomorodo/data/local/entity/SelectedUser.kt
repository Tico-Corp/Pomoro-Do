package com.tico.pomorodo.data.local.entity

data class SelectedUser(
    val id: String,
    val name: String,
    val profileUrl: String? = null,
    val selected: Boolean
)