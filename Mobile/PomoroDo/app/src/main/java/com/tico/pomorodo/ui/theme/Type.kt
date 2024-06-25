package com.tico.pomorodo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tico.pomorodo.R

val laundryGothic = FontFamily(
    Font(R.font.laundry_gothic_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.laundry_gothic_regular, FontWeight.Normal, FontStyle.Normal)
)

val roboto = FontFamily(
    Font(R.font.roboto_medium, FontWeight.Medium, FontStyle.Normal)
)

val singleDay = FontFamily(
    Font(R.font.singleday_regular, FontWeight.Normal, FontStyle.Normal)
)

// Set of Material typography styles to start with
internal val Typography = PomoroDoTypography(
    laundryGothicRegular48 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 55.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular30 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular28 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular26 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular24 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular20 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular18 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular16 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular14 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular12 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicRegular10 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold48 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 55.2.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold30 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 34.5.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold28 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.2.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold26 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 29.9.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold24 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 27.6.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold20 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold18 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.7.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold16 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 18.4.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold14 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.1.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold12 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 13.8.sp,
        letterSpacing = 0.5.sp
    ),
    laundryGothicBold10 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 11.5.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular48 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 55.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular30 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular28 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular26 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular24 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular20 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular18 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular16 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular14 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular12 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    ),
    singleDayRegular10 = TextStyle(
        fontFamily = singleDay,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    ),
    robotoMedium14 = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)

@Immutable
data class PomoroDoTypography(
    val laundryGothicRegular48: TextStyle,
    val laundryGothicRegular30: TextStyle,
    val laundryGothicRegular28: TextStyle,
    val laundryGothicRegular26: TextStyle,
    val laundryGothicRegular24: TextStyle,
    val laundryGothicRegular20: TextStyle,
    val laundryGothicRegular18: TextStyle,
    val laundryGothicRegular16: TextStyle,
    val laundryGothicRegular14: TextStyle,
    val laundryGothicRegular12: TextStyle,
    val laundryGothicRegular10: TextStyle,
    val laundryGothicBold48: TextStyle,
    val laundryGothicBold30: TextStyle,
    val laundryGothicBold28: TextStyle,
    val laundryGothicBold26: TextStyle,
    val laundryGothicBold24: TextStyle,
    val laundryGothicBold20: TextStyle,
    val laundryGothicBold18: TextStyle,
    val laundryGothicBold16: TextStyle,
    val laundryGothicBold14: TextStyle,
    val laundryGothicBold12: TextStyle,
    val laundryGothicBold10: TextStyle,
    val singleDayRegular48: TextStyle,
    val singleDayRegular30: TextStyle,
    val singleDayRegular28: TextStyle,
    val singleDayRegular26: TextStyle,
    val singleDayRegular24: TextStyle,
    val singleDayRegular20: TextStyle,
    val singleDayRegular18: TextStyle,
    val singleDayRegular16: TextStyle,
    val singleDayRegular14: TextStyle,
    val singleDayRegular12: TextStyle,
    val singleDayRegular10: TextStyle,
    val robotoMedium14: TextStyle,
)

val LocalPomoroDoTypography = staticCompositionLocalOf {
    PomoroDoTypography(
        laundryGothicRegular48 = TextStyle.Default,
        laundryGothicRegular30 = TextStyle.Default,
        laundryGothicRegular28 = TextStyle.Default,
        laundryGothicRegular26 = TextStyle.Default,
        laundryGothicRegular24 = TextStyle.Default,
        laundryGothicRegular20 = TextStyle.Default,
        laundryGothicRegular18 = TextStyle.Default,
        laundryGothicRegular16 = TextStyle.Default,
        laundryGothicRegular14 = TextStyle.Default,
        laundryGothicRegular12 = TextStyle.Default,
        laundryGothicRegular10 = TextStyle.Default,
        laundryGothicBold48 = TextStyle.Default,
        laundryGothicBold30 = TextStyle.Default,
        laundryGothicBold28 = TextStyle.Default,
        laundryGothicBold26 = TextStyle.Default,
        laundryGothicBold24 = TextStyle.Default,
        laundryGothicBold20 = TextStyle.Default,
        laundryGothicBold18 = TextStyle.Default,
        laundryGothicBold16 = TextStyle.Default,
        laundryGothicBold14 = TextStyle.Default,
        laundryGothicBold12 = TextStyle.Default,
        laundryGothicBold10 = TextStyle.Default,
        singleDayRegular48 = TextStyle.Default,
        singleDayRegular30 = TextStyle.Default,
        singleDayRegular28 = TextStyle.Default,
        singleDayRegular26 = TextStyle.Default,
        singleDayRegular24 = TextStyle.Default,
        singleDayRegular20 = TextStyle.Default,
        singleDayRegular18 = TextStyle.Default,
        singleDayRegular16 = TextStyle.Default,
        singleDayRegular14 = TextStyle.Default,
        singleDayRegular12 = TextStyle.Default,
        singleDayRegular10 = TextStyle.Default,
        robotoMedium14 = TextStyle.Default,
    )
}