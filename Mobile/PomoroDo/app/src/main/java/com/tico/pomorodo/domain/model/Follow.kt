package com.tico.pomorodo.domain.model

data class Follow(
    val followId: Long,
    val profileUrl: String? = null,
    val name: String,
    var isFollowing: Boolean
)
