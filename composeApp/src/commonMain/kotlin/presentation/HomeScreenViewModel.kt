package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Channel
import presentation.repo.Database
import presentation.repo.IptvApi

class HomeScreenViewModel(private val database: Database, private val api: IptvApi) :
    ScreenModel {
    private val _stateChannels = MutableStateFlow<List<Channel>>(emptyList())
    val channelState = _stateChannels.asStateFlow()

    override fun onDispose() {
        super.onDispose()
        api.onClose()
    }

    private suspend fun getAllChannels(forceReload: Boolean): List<Channel> {
        val cachedChannels = database.getAllChannels()
        return if (cachedChannels.isNotEmpty() && !forceReload) {
            cachedChannels
        } else {
            val streamMap =
                api.getAllStream().filter { it.channel.isNotBlank() }.associateBy { it.channel }
            val blockedList = api.getAllBlockedList().associateBy { it.channel }

            api.getAllChannels().filter { !it.isNsfw && streamMap.containsKey(it.id) }.filterNot {
                blockedList.containsKey(it.id)
            }
                .mapNotNull { channel ->
                    streamMap[channel.id]?.let {
                        channel.copy(url = it.url)
                    }
                }.also {
                    database.clearDatabase()
                    database.createChannels(it)
                }
        }
    }

    fun updateChannels(forceReload: Boolean) {
        screenModelScope.launch {
            val channelList = getAllChannels(forceReload)
            _stateChannels.value = channelList
        }
    }

    fun getStreamLinkByChannelId(channelId: String): Channel? {
        return _stateChannels.value.firstOrNull {
            it.id == channelId
        }
    }
}