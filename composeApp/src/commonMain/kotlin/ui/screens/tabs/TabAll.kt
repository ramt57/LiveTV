package ui.screens.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.HomeScreenViewModel
import ui.ChannelView
import ui.screens.ScreenWatchChannel

class TabAll(private val navigator: Navigator) : Tab {
    @Composable
    override fun Content() {
        val screenModel = navigator.getNavigatorScreenModel<HomeScreenViewModel>()
        AllChannelsList(navigator, screenModel)
    }

    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            val title = "All"
            val index: UShort = 0u
            return TabOptions(
                index = index,
                icon = icon,
                title = title
            )
        }
}


@Composable
private fun AllChannelsList(
    navigator: Navigator,
    tvViewModel: HomeScreenViewModel
) {
    LaunchedEffect(tvViewModel) {
        tvViewModel.updateChannels(false)
    }
    val channelState by tvViewModel.filteredChannels.collectAsState()
    var selectedChannel by remember { mutableStateOf(channelState.firstOrNull()?.id) }
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(channelState, key = {
                    it.id
                }) { channel ->
                    ChannelView(channel = channel) {
                        selectedChannel = it
                        navigator.push(ScreenWatchChannel(channel))
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}