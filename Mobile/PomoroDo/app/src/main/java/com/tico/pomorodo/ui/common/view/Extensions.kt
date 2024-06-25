package com.tico.pomorodo.ui.common.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.User

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

fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)