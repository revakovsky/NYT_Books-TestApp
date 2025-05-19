package com.revakovskyi.core.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightThemeColors = lightColorScheme(
    primary = LightCardBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    background = LightCardBackground,
    onBackground = LightPrimaryText,
    secondary = LightSecondaryText,
)

private val darkThemeColors = darkColorScheme(
    primary = DarkCardBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    background = DarkCardBackground,
    onBackground = DarkPrimaryText,
    secondary = DarkSecondaryText,
)


@Composable
fun NYTBooksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme =
        if (darkTheme) darkThemeColors
        else lightThemeColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}