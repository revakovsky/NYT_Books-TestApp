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
    primary = BackgroundWhite_Light,
    background = BackgroundWhite_Light,
    onBackground = OnBackgroundBlack_Light,
    surface = SurfaceWhite_Light,
    onSurface = OnSurfaceBlack_Light,
    secondary = SecondaryGray_Light,
)

private val darkThemeColors = darkColorScheme(
    primary = BackgroundBlack_Dark,
    background = BackgroundBlack_Dark,
    onBackground = OnBackgroundWhite_Dark,
    surface = SurfaceBlack_Dark,
    onSurface = OnSurfaceWhite_Dark,
    secondary = SecondaryGray_Dark,
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