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
import androidx.compose.ui.unit.sp

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
fun getScaledTypography(fontScale: String): androidx.compose.material3.Typography {
    val baseTypography = androidx.compose.material3.Typography()
    return baseTypography.copy(
        displayLarge = baseTypography.displayLarge.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.displayLarge.fontSize * 0.85f
                "large" -> baseTypography.displayLarge.fontSize * 1.15f
                "xlarge" -> baseTypography.displayLarge.fontSize * 1.3f
                else -> baseTypography.displayLarge.fontSize
            }
        ),
        displayMedium = baseTypography.displayMedium.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.displayMedium.fontSize * 0.85f
                "large" -> baseTypography.displayMedium.fontSize * 1.15f
                "xlarge" -> baseTypography.displayMedium.fontSize * 1.3f
                else -> baseTypography.displayMedium.fontSize
            }
        ),
        displaySmall = baseTypography.displaySmall.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.displaySmall.fontSize * 0.85f
                "large" -> baseTypography.displaySmall.fontSize * 1.15f
                "xlarge" -> baseTypography.displaySmall.fontSize * 1.3f
                else -> baseTypography.displaySmall.fontSize
            }
        ),
        headlineLarge = baseTypography.headlineLarge.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.headlineLarge.fontSize * 0.85f
                "large" -> baseTypography.headlineLarge.fontSize * 1.15f
                "xlarge" -> baseTypography.headlineLarge.fontSize * 1.3f
                else -> baseTypography.headlineLarge.fontSize
            }
        ),
        headlineMedium = baseTypography.headlineMedium.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.headlineMedium.fontSize * 0.85f
                "large" -> baseTypography.headlineMedium.fontSize * 1.15f
                "xlarge" -> baseTypography.headlineMedium.fontSize * 1.3f
                else -> baseTypography.headlineMedium.fontSize
            }
        ),
        headlineSmall = baseTypography.headlineSmall.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.headlineSmall.fontSize * 0.85f
                "large" -> baseTypography.headlineSmall.fontSize * 1.15f
                "xlarge" -> baseTypography.headlineSmall.fontSize * 1.3f
                else -> baseTypography.headlineSmall.fontSize
            }
        ),
        titleLarge = baseTypography.titleLarge.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.titleLarge.fontSize * 0.85f
                "large" -> baseTypography.titleLarge.fontSize * 1.15f
                "xlarge" -> baseTypography.titleLarge.fontSize * 1.3f
                else -> baseTypography.titleLarge.fontSize
            }
        ),
        titleMedium = baseTypography.titleMedium.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.titleMedium.fontSize * 0.85f
                "large" -> baseTypography.titleMedium.fontSize * 1.15f
                "xlarge" -> baseTypography.titleMedium.fontSize * 1.3f
                else -> baseTypography.titleMedium.fontSize
            }
        ),
        titleSmall = baseTypography.titleSmall.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.titleSmall.fontSize * 0.85f
                "large" -> baseTypography.titleSmall.fontSize * 1.15f
                "xlarge" -> baseTypography.titleSmall.fontSize * 1.3f
                else -> baseTypography.titleSmall.fontSize
            }
        ),
        bodyLarge = baseTypography.bodyLarge.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.bodyLarge.fontSize * 0.85f
                "large" -> baseTypography.bodyLarge.fontSize * 1.15f
                "xlarge" -> baseTypography.bodyLarge.fontSize * 1.3f
                else -> baseTypography.bodyLarge.fontSize
            }
        ),
        bodyMedium = baseTypography.bodyMedium.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.bodyMedium.fontSize * 0.85f
                "large" -> baseTypography.bodyMedium.fontSize * 1.15f
                "xlarge" -> baseTypography.bodyMedium.fontSize * 1.3f
                else -> baseTypography.bodyMedium.fontSize
            }
        ),
        bodySmall = baseTypography.bodySmall.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.bodySmall.fontSize * 0.85f
                "large" -> baseTypography.bodySmall.fontSize * 1.15f
                "xlarge" -> baseTypography.bodySmall.fontSize * 1.3f
                else -> baseTypography.bodySmall.fontSize
            }
        ),
        labelLarge = baseTypography.labelLarge.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.labelLarge.fontSize * 0.85f
                "large" -> baseTypography.labelLarge.fontSize * 1.15f
                "xlarge" -> baseTypography.labelLarge.fontSize * 1.3f
                else -> baseTypography.labelLarge.fontSize
            }
        ),
        labelMedium = baseTypography.labelMedium.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.labelMedium.fontSize * 0.85f
                "large" -> baseTypography.labelMedium.fontSize * 1.15f
                "xlarge" -> baseTypography.labelMedium.fontSize * 1.3f
                else -> baseTypography.labelMedium.fontSize
            }
        ),
        labelSmall = baseTypography.labelSmall.copy(
            fontSize = when (fontScale) {
                "small" -> baseTypography.labelSmall.fontSize * 0.85f
                "large" -> baseTypography.labelSmall.fontSize * 1.15f
                "xlarge" -> baseTypography.labelSmall.fontSize * 1.3f
                else -> baseTypography.labelSmall.fontSize
            }
        )
    )
}

@Composable
fun PlaySongHymBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontScale: String = "medium",
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
    
    val scaledTypography = getScaledTypography(fontScale)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = scaledTypography,
        content = content
    )
}