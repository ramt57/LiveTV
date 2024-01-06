package presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.Channel
import model.Stream

class TVViewModel(databaseDriverFactory: DatabaseDriverFactory) : ViewModel() {
    private val database = Database(databaseDriverFactory)
    private val api = IptvApi()

    private val _stateChannels = MutableStateFlow<List<Channel>>(emptyList())
    val channelState = _stateChannels.asStateFlow()

    private suspend fun getAllChannels(forceReload: Boolean): List<Channel> {
        val cachedChannels = database.getAllChannels()
        return if (cachedChannels.isNotEmpty() && !forceReload) {
            cachedChannels
        } else {
            val streamList = api.GetAllStream().filter { it.channel.isNotBlank() }
            // Create a map from channel ID to Stream
            val streamMap = streamList.associateBy { it.channel }

            api.GetAllChannels().filter { !it.isNsfw && streamMap.containsKey(it.id) }
                .mapNotNull { channel -> streamMap[channel.id]?.let {
                    channel.copy(url = it.url)
                }}.also {
                    database.clearDatabase()
                    database.createChannels(it)
                }
        }
    }

    fun updateChannels(forceReload: Boolean) {
        viewModelScope.launch {
            val channelList = getAllChannels(forceReload)
            _stateChannels.value = channelList
        }
    }

    fun getStreamLinkByChannelId(channelId: String): Channel? {
        return _stateChannels.value.firstOrNull {
            it.id == channelId
        }
    }

    override fun onCleared() {
        api.onClose()
    }

}
