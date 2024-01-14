package ui.screens.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class TabFavourite(private val navigator: Navigator) : Tab{
    @Composable
    override fun Content() {
        Column() {
            Text(text = "Not yet Implemented")
        }
    }


    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            val title = "Favourite"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}