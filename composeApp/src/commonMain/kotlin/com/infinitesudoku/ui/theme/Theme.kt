package com.infinitesudoku.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Primary colors - Clean blue for a calm, focused experience
private val PrimaryLight = Color(0xFF1A73E8)
private val OnPrimaryLight = Color(0xFFFFFFFF)
private val PrimaryContainerLight = Color(0xFFD3E3FD)
private val OnPrimaryContainerLight = Color(0xFF001D36)

private val PrimaryDark = Color(0xFFA8C7FA)
private val OnPrimaryDark = Color(0xFF003258)
private val PrimaryContainerDark = Color(0xFF004A77)
private val OnPrimaryContainerDark = Color(0xFFD3E3FD)

// Secondary colors
private val SecondaryLight = Color(0xFF5F6368)
private val OnSecondaryLight = Color(0xFFFFFFFF)
private val SecondaryContainerLight = Color(0xFFE8EAED)
private val OnSecondaryContainerLight = Color(0xFF1D1D1D)

private val SecondaryDark = Color(0xFFC4C7C5)
private val OnSecondaryDark = Color(0xFF2E312F)
private val SecondaryContainerDark = Color(0xFF444746)
private val OnSecondaryContainerDark = Color(0xFFE0E3E1)

// Error colors - Colorblind-safe (not pure red)
private val ErrorLight = Color(0xFFBA1A1A)
private val OnErrorLight = Color(0xFFFFFFFF)
private val ErrorContainerLight = Color(0xFFFFDAD6)
private val OnErrorContainerLight = Color(0xFF410002)

private val ErrorDark = Color(0xFFFFB4AB)
private val OnErrorDark = Color(0xFF690005)
private val ErrorContainerDark = Color(0xFF93000A)
private val OnErrorContainerDark = Color(0xFFFFDAD6)

// Background and surface colors
private val BackgroundLight = Color(0xFFFAFAFA)
private val OnBackgroundLight = Color(0xFF1A1C1E)
private val SurfaceLight = Color(0xFFFFFFFF)
private val OnSurfaceLight = Color(0xFF1A1C1E)

private val BackgroundDark = Color(0xFF1A1C1E)
private val OnBackgroundDark = Color(0xFFE2E2E6)
private val SurfaceDark = Color(0xFF121316)
private val OnSurfaceDark = Color(0xFFE2E2E6)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
)

@Composable
fun InfiniteSudokuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
