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
import model.Channel
import model.Stream

class TVViewModel : ViewModel() {
    private val _stateChannels = MutableStateFlow<List<Channel>>(emptyList())
    val channelState = _stateChannels.asStateFlow()

    private val _stateStreams = MutableStateFlow<List<Stream>>(emptyList())

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    fun updateChannels() {
        viewModelScope.launch {
            val streamList = GetAllStream().filter { it.channel.isNotBlank() }
            val channelList = GetAllChannels()

            // Create a map from channel ID to Stream
            val streamMap = streamList.associateBy { it.channel }

            _stateStreams.value = streamList

            _stateChannels.value = channelList
                .filter { !it.isNsfw && streamMap.containsKey(it.id) }
                .mapNotNull { channel -> streamMap[channel.id]?.let { channel } }
        }
    }

    fun getStreamLinkByChannelId(channelId: String): Stream? {
        return _stateStreams.value.firstOrNull {
            it.channel == channelId
        }
    }

    override fun onCleared() {
        httpClient.close()
    }

    private suspend fun GetAllChannels(): List<Channel> {
        return httpClient.get("https://iptv-org.github.io/api/channels.json").body<List<Channel>>()
    }

    private suspend fun GetAllStream(): List<Stream> {
        return httpClient.get("https://iptv-org.github.io/api/streams.json").body<List<Stream>>()
    }
}
