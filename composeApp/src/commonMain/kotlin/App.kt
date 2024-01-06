import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.DatabaseDriverFactory
import presentation.TVViewModel
import ui.home.AppBarMenu
import ui.home.AppLogo
import ui.home.AppVideo
import ui.home.ChannelView
import ui.home.TabItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    val tvViewModel = getViewModel(
        key = Unit,
        factory = viewModelFactory {
            TVViewModel(databaseDriverFactory)
        }
    )
    LaunchedEffect(tvViewModel) {
        tvViewModel.updateChannels(false)
    }
    val channelState by tvViewModel.channelState.collectAsState()
    var selectedChannel by remember { mutableStateOf(channelState.firstOrNull()?.id) }
    var selectedTab: TabItem by remember { mutableStateOf(TabItem.Country) }
    Surface {
        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,
                        title = {
                            Row {
                                AppLogo()
                                AppBarMenu(selectedTab) {
                                    selectedTab = it
                                }
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
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
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
