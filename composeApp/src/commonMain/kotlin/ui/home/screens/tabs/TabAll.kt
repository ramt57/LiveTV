package ui.home.screens.tabs

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
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.repo.Database
import presentation.repo.DatabaseDriverFactory
import presentation.HomeScreenViewModel
import presentation.repo.IptvApi
import ui.home.AppVideo
import ui.home.ChannelView
import ui.home.screens.ScreenChannelDetail

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
    val channelState by tvViewModel.channelState.collectAsState()
    var selectedChannel by remember { mutableStateOf(channelState.firstOrNull()?.id) }
    Column {
        val Channel = selectedChannel?.let { tvViewModel.getStreamLinkByChannelId(it) }
        AppVideo(Channel)
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
                        navigator.push(ScreenChannelDetail(channel))
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}