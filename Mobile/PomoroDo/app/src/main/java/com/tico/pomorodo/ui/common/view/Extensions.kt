package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import com.tico.pomorodo.data.local.entity.SelectedUser
import com.tico.pomorodo.data.local.entity.User

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                doOnClear()
                focusManager.clearFocus()
            }
        )
    }
}

fun String.getNoSpace(): String = this.replace(" ", "")

fun User.toSelectedUser(selected: Boolean = false): SelectedUser =
    SelectedUser(
        id = this.id,
        name = this.name,
        profileUrl = this.profileUrl,
        selected = selected
    )

fun SelectedUser.toUser(): User =
    User(id = this.id, name = this.name, profileUrl = this.profileUrl)