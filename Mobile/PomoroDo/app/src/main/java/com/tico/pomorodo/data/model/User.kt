package com.tico.pomorodo.data.model

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val profileUrl: String? = null
)