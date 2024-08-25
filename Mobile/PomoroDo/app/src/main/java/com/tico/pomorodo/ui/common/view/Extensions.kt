package com.tico.pomorodo.ui.common.view

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tico.pomorodo.data.model.SelectedUser
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed(
    factory = {
        val interactionSource = remember { MutableInteractionSource() }

        this.then(
            Modifier.clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)

fun Modifier.clickableWithRipple(
    roundedCornerRadius: Dp = 0.dp,
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed(
    factory = {
        val interactionSource = remember { MutableInteractionSource() }

        this.then(
            Modifier
                .clip(RoundedCornerShape(roundedCornerRadius))
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = ripple(
                        bounded = true,
                    ),
                    onClick = { onClick() }
                )
        )
    }
)

fun LocalDateTime.getTimeFormat(): String = format(DateTimeFormatter.ofPattern("a h:mm", Locale.US))

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
    val imageFileName = "Profile_Image_$timeStamp"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}

fun Context.uriToFile(uri: Uri): File? {
    val inputStream = this.contentResolver.openInputStream(uri) ?: return null
    val file = File(this.externalCacheDir, getFileName(uri))
    file.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return file
}

fun Context.getFileName(uri: Uri): String {
    val cursor = this.contentResolver.query(uri, null, null, null, null)
    val fileName = cursor?.use { c ->
        c.moveToFirst()
        val index = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index != -1) c.getString(index)
        else null
    }
    return fileName ?: "unknown_file"
}

fun Context.executeToast(@StringRes messageId: Int) {
    Toast.makeText(this, this.getString(messageId), Toast.LENGTH_SHORT).show()
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffset by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1000)), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                PomoroDoTheme.colorScheme.gray70,
                PomoroDoTheme.colorScheme.gray50,
                PomoroDoTheme.colorScheme.gray70
            ),
            start = Offset(startOffset, 0f),
            end = Offset(startOffset + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}