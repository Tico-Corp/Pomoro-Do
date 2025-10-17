package com.tico.pomorodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tico.pomorodo.data.model.User
import kotlinx.serialization.Serializable

@Entity(tableName = "user_table")
@Serializable
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val profileUrl: String? = null
)

fun UserEntity.toUser() = User(id, name, profileUrl)