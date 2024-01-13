package presentation.repo

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.BlockedChannel
import model.CategoryItem
import model.Channel
import model.CountryItem
import model.GuideItem
import model.Language
import model.Stream
import presentation.repo.di.IpvtvNetwork

class IptvApi(val httpClient: HttpClient) {
    companion object {
        private const val CHANNEL = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.CHANNEL)
        private const val STREAM = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.STREAM)
        private const val BLOCKED = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.BLOCKED)
        private const val CATEGORY = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.CATEGORY)
        private const val LANGUAGE = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.LANGUAGE)
        private const val COUNTRY = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.COUNTRY)
        private const val GUIDE = IpvtvNetwork.URL.plus(IpvtvNetwork.EndPoint.GUIDE)
    }

    internal suspend fun getAllChannels(): List<Channel> {
        return httpClient.get(CHANNEL).body<List<Channel>>()
    }

    internal suspend fun getAllStream(): List<Stream> {
        return httpClient.get(STREAM).body<List<Stream>>()
    }

    internal suspend fun getAllBlockedList(): List<BlockedChannel> {
        return httpClient.get(BLOCKED).body<List<BlockedChannel>>()
    }

    internal suspend fun getCategoryList(): List<CategoryItem> {
        return httpClient.get(CATEGORY).body<List<CategoryItem>>()
    }

    internal suspend fun getLanguageList(): List<Language> {
        return httpClient.get(LANGUAGE).body<List<Language>>()
    }

    internal suspend fun getCountriesList(): List<CountryItem> {
        return httpClient.get(COUNTRY).body<List<CountryItem>>()
    }

    internal suspend fun getChannelGuideList(): List<GuideItem> {
        return httpClient.get(GUIDE).body<List<GuideItem>>()
    }

    fun onClose() {
        httpClient.close()
    }
}