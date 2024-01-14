package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import ui.theme.md_theme_dark_background
import ui.theme.md_theme_dark_error
import ui.theme.md_theme_dark_errorContainer
import ui.theme.md_theme_dark_inverseOnSurface
import ui.theme.md_theme_dark_inversePrimary
import ui.theme.md_theme_dark_inverseSurface
import ui.theme.md_theme_dark_onBackground
import ui.theme.md_theme_dark_onError
import ui.theme.md_theme_dark_onErrorContainer
import ui.theme.md_theme_dark_onPrimary
import ui.theme.md_theme_dark_onPrimaryContainer
import ui.theme.md_theme_dark_onSecondary
import ui.theme.md_theme_dark_onSecondaryContainer
import ui.theme.md_theme_dark_onSurface
import ui.theme.md_theme_dark_onSurfaceVariant
import ui.theme.md_theme_dark_onTertiary
import ui.theme.md_theme_dark_onTertiaryContainer
import ui.theme.md_theme_dark_outline
import ui.theme.md_theme_dark_outlineVariant
import ui.theme.md_theme_dark_primary
import ui.theme.md_theme_dark_primaryContainer
import ui.theme.md_theme_dark_scrim
import ui.theme.md_theme_dark_secondary
import ui.theme.md_theme_dark_secondaryContainer
import ui.theme.md_theme_dark_surface
import ui.theme.md_theme_dark_surfaceTint
import ui.theme.md_theme_dark_surfaceVariant
import ui.theme.md_theme_dark_tertiary
import ui.theme.md_theme_dark_tertiaryContainer
import ui.theme.md_theme_light_background
import ui.theme.md_theme_light_error
import ui.theme.md_theme_light_errorContainer
import ui.theme.md_theme_light_inverseOnSurface
import ui.theme.md_theme_light_inversePrimary
import ui.theme.md_theme_light_inverseSurface
import ui.theme.md_theme_light_onBackground
import ui.theme.md_theme_light_onError
import ui.theme.md_theme_light_onErrorContainer
import ui.theme.md_theme_light_onPrimary
import ui.theme.md_theme_light_onPrimaryContainer
import ui.theme.md_theme_light_onSecondary
import ui.theme.md_theme_light_onSecondaryContainer
import ui.theme.md_theme_light_onSurface
import ui.theme.md_theme_light_onSurfaceVariant
import ui.theme.md_theme_light_onTertiary
import ui.theme.md_theme_light_onTertiaryContainer
import ui.theme.md_theme_light_outline
import ui.theme.md_theme_light_outlineVariant
import ui.theme.md_theme_light_primary
import ui.theme.md_theme_light_primaryContainer
import ui.theme.md_theme_light_scrim
import ui.theme.md_theme_light_secondary
import ui.theme.md_theme_light_secondaryContainer
import ui.theme.md_theme_light_surface
import ui.theme.md_theme_light_surfaceTint
import ui.theme.md_theme_light_surfaceVariant
import ui.theme.md_theme_light_tertiary
import ui.theme.md_theme_light_tertiaryContainer


private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,
    outline = md_theme_light_outline,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    inverseSurface = md_theme_light_inverseSurface,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,
    outline = md_theme_dark_outline,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    inverseSurface = md_theme_dark_inverseSurface,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && isDynamicColorAvailable() -> {
            getDynamicColors(useDarkTheme)
        }

        useDarkTheme -> DarkColors
        else -> LightColors
    }


    if (colorScheme != null) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

@Composable
expect fun getDynamicColors(useDarkTheme: Boolean): ColorScheme?
expect fun isDynamicColorAvailable(): Boolean