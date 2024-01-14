package ui.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Channel
import ui.AppVideo

class ScreenWatchChannel(val channel: Channel): Screen {
    @Composable
    override fun Content() {
        AppVideo(channel = channel)
    }
}