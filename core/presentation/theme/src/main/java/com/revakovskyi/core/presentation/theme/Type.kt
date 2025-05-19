package com.revakovskyi.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

private val AbhayaLibre = FontFamily(
    Font(R.font.abhayalibre_regular, weight = FontWeight.Normal),
    Font(R.font.abhayalibre_semibold, weight = FontWeight.SemiBold),
    Font(R.font.abhayalibre_bold, weight = FontWeight.Bold),
)

private val Grenzegotich = FontFamily(
    Font(R.font.grenzegotisch_semibold, weight = FontWeight.SemiBold),
)


val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = Grenzegotich,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = AbhayaLibre,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
)
