package ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getDynamicColors(useDarkTheme: Boolean): ColorScheme? {
    return null
}

actual fun isDynamicColorAvailable(): Boolean {
    return false
}