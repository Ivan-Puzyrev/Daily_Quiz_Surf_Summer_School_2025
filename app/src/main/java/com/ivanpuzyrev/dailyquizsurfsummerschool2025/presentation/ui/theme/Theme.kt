package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    background = Indigo,
    surface = Indigo,
    primary = White,
    onPrimary = Black,
    surfaceContainerHighest = White


)

@Composable
fun DailyQuizSurfSummerSchool2025Theme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}