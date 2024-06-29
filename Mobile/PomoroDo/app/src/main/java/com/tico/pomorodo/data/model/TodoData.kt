package com.tico.pomorodo.data.model

data class TodoData(
    val id: String,
    val name: String,
    var state: TodoState,
    val completeGroupNumber: Int? = null,
    val likedNumber: Int = 0,
)

enum class TodoState {
    UNCHECKED, GOING, CHECKED
}