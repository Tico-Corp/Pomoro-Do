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
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
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

fun LocalDate.daysInMonth(): Int {
    return when (this.month) {
        Month.FEBRUARY -> if (this.year.isLeapYear()) 29 else 28
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }
}

fun Int.isLeapYear(): Boolean {
    return (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
}

fun LocalDate.atStartOfMonth(): LocalDate {
    return LocalDate(this.year, this.month, 1)
}

fun LocalDate.atEndOfMonth(): LocalDate {
    val daysInMonth = this.daysInMonth()
    return LocalDate(this.year, this.month, daysInMonth)
}

fun kotlinx.datetime.LocalDateTime.toTimeZoneOf5AM(): LocalDate {
    val fiveAM = kotlinx.datetime.LocalDateTime(this.date, LocalTime(5, 0))
    return if (this < fiveAM) {
        this.date.minus(DatePeriod(days = 1))
    } else {
        this.date
    }
}

fun LocalDate.weekOfMonth(): Int {
    val firstDayOfMonth = this.atStartOfMonth().dayOfWeek.isoDayNumber
    return ((this.dayOfMonth + firstDayOfMonth - 2) / 7) + 1
}


private const val NICKNAME_MIN_RANGE = 2
private const val NICKNAME_MAX_RANGE = 10

fun String.checkNameValidation(): NameErrorType {
    val regex = "[^ㄱ-ㅎ가-힣a-zA-Z0-9_]".toRegex()
    return if (this.length !in NICKNAME_MIN_RANGE..NICKNAME_MAX_RANGE) {
        NameErrorType.RANGE_ERROR
    } else if (regex.containsMatchIn(this)) {
        NameErrorType.INVALID_ERROR
    } else {
        NameErrorType.NONE
    }
}