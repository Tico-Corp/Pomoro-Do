package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.domain.model.Follow
import kotlinx.serialization.Serializable

@Serializable
data class FollowResponse(
    val followId: Int,
    val profileUrl: String? = null,
    val name: String,
    var isFollowing: Boolean
)

fun FollowResponse.toFollow() = Follow(
    followId = followId,
    profileUrl = profileUrl,
    name = name,
    isFollowing = isFollowing
)
