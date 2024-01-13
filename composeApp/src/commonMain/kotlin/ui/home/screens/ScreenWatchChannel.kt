package ui.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class ScreenWatchChannel: Screen {
    @Composable
    override fun Content() {
        Column() {
            Text(text = "Video Player")
        }
    }
}