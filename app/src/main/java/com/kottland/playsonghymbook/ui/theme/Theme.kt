package com.kottland.playsonghymbook.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val VintageDarkColorScheme = darkColorScheme(
    primary = VintageGoldDark,
    onPrimary = VintageCharcoal,
    primaryContainer = VintageAmberDark,
    onPrimaryContainer = VintageCharcoal,
    secondary = VintageBrownDark,
    onSecondary = VintageCharcoal,
    secondaryContainer = VintageBrownDark,
    onSecondaryContainer = VintageCream,
    tertiary = VintageGreenLight,
    onTertiary = VintageCharcoal,
    tertiaryContainer = VintageGreen,
    onTertiaryContainer = VintageCream,
    background = VintageCharcoal,
    onBackground = VintageOffWhite,
    surface = VintageSlate,
    onSurface = VintageCream,
    surfaceVariant = VintageSlate,
    onSurfaceVariant = VintageCream,
    outline = VintageAmberDark,
    error = VintageRedLight,
    onError = VintageCharcoal
)

private val VintageLightColorScheme = lightColorScheme(
    primary = VintageGold,
    onPrimary = VintageInk,
    primaryContainer = VintageAmber,
    onPrimaryContainer = VintageParchment,
    secondary = VintageBrown,
    onSecondary = VintageParchment,
    secondaryContainer = VintageBrown,
    onSecondaryContainer = VintageParchment,
    tertiary = VintageGreen,
    onTertiary = VintageParchment,
    tertiaryContainer = VintageGreenLight,
    onTertiaryContainer = VintageInk,
    background = VintageSepia,
    onBackground = VintageText,
    surface = VintageParchment,
    onSurface = VintageInk,
    surfaceVariant = VintageParchment,
    onSurfaceVariant = VintageText,
    outline = VintageAmber,
    error = VintageRed,
    onError = VintageParchment
)

@Composable
fun PlaySongHymBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Vintage theme - dynamic color disabled for consistent vintage appearance
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> VintageDarkColorScheme
        else -> VintageLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}