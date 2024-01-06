package presentation

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Channel
import model.Stream

class IptvApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    internal suspend fun GetAllChannels(): List<Channel> {
        return httpClient.get("https://iptv-org.github.io/api/channels.json").body<List<Channel>>()
    }

    internal suspend fun GetAllStream(): List<Stream> {
        return httpClient.get("https://iptv-org.github.io/api/streams.json").body<List<Stream>>()
    }

    fun onClose(){
        httpClient.close()
    }
}