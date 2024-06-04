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
    Font(R.font.laundrygothic_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.laundrygothic_regular, FontWeight.Normal, FontStyle.Normal)
)

// Set of Material typography styles to start with
internal val Typography = PomoroDoTypography(
    regular48 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 55.sp,
        letterSpacing = 0.5.sp
    ),
    regular30 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.5.sp
    ),
    regular28 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    regular26 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp
    ),
    regular24 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    regular20 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    regular18 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
    regular16 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    regular14 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    regular12 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    ),
    regular10 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    ),
    bold48 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 55.2.sp,
        letterSpacing = 0.5.sp
    ),
    bold30 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 34.5.sp,
        letterSpacing = 0.5.sp
    ),
    bold28 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.2.sp,
        letterSpacing = 0.5.sp
    ),
    bold26 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 29.9.sp,
        letterSpacing = 0.5.sp
    ),
    bold24 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 27.6.sp,
        letterSpacing = 0.5.sp
    ),
    bold20 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.5.sp
    ),
    bold18 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 20.7.sp,
        letterSpacing = 0.5.sp
    ),
    bold16 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 18.4.sp,
        letterSpacing = 0.5.sp
    ),
    bold14 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.1.sp,
        letterSpacing = 0.5.sp
    ),
    bold12 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 13.8.sp,
        letterSpacing = 0.5.sp
    ),
    bold10 = TextStyle(
        fontFamily = laundryGothic,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 11.5.sp,
        letterSpacing = 0.5.sp
    ),
)

@Immutable
data class PomoroDoTypography(
    val regular48: TextStyle,
    val regular30: TextStyle,
    val regular28: TextStyle,
    val regular26: TextStyle,
    val regular24: TextStyle,
    val regular20: TextStyle,
    val regular18: TextStyle,
    val regular16: TextStyle,
    val regular14: TextStyle,
    val regular12: TextStyle,
    val regular10: TextStyle,
    val bold48: TextStyle,
    val bold30: TextStyle,
    val bold28: TextStyle,
    val bold26: TextStyle,
    val bold24: TextStyle,
    val bold20: TextStyle,
    val bold18: TextStyle,
    val bold16: TextStyle,
    val bold14: TextStyle,
    val bold12: TextStyle,
    val bold10: TextStyle,
)

val LocalPomoroDoTypography = staticCompositionLocalOf {
    PomoroDoTypography(
        regular48 = TextStyle.Default,
        regular30 = TextStyle.Default,
        regular28 = TextStyle.Default,
        regular26 = TextStyle.Default,
        regular24 = TextStyle.Default,
        regular20 = TextStyle.Default,
        regular18 = TextStyle.Default,
        regular16 = TextStyle.Default,
        regular14 = TextStyle.Default,
        regular12 = TextStyle.Default,
        regular10 = TextStyle.Default,
        bold48 = TextStyle.Default,
        bold30 = TextStyle.Default,
        bold28 = TextStyle.Default,
        bold26 = TextStyle.Default,
        bold24 = TextStyle.Default,
        bold20 = TextStyle.Default,
        bold18 = TextStyle.Default,
        bold16 = TextStyle.Default,
        bold14 = TextStyle.Default,
        bold12 = TextStyle.Default,
        bold10 = TextStyle.Default,
    )
}