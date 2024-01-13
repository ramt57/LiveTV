package ui.home.screens.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.home.screens.ScreenWatchChannel

object TabCountry : Tab{
    @Composable
    override fun Content() {
        Column() {
            Text(text = "Your Country")
        }
    }


    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.PlayArrow)
            val title = "Country"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}