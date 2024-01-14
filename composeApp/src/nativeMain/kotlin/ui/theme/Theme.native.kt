package ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

actual fun isDynamicColorAvailable(): Boolean {
    return false
}

@Composable
actual fun getDynamicColors(useDarkTheme: Boolean): ColorScheme? {
    TODO()
}