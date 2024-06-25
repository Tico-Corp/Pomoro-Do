package com.tico.pomorodo.data.model

data class SelectedUser(
    val id: String,
    val name: String,
    val profileUrl: String? = null,
    val selected: Boolean
)