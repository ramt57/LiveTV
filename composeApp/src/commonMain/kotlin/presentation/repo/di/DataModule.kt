package presentation.repo.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.repo.Database
import presentation.repo.IptvApi

object IpvtvNetwork {
    const val URL = "https://iptv-org.github.io/api/"

    object EndPoint {
        const val CHANNEL = "channels.json"
        const val STREAM = "streams.json"
        const val BLOCKED = "blocklist.json"
        const val CATEGORY = "categories.json"
        const val LANGUAGE = "languages.json"
        const val COUNTRY = "countries.json"
        const val GUIDE = "guides.json"
    }
}

val httpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}

val apiModule = module {
    single<IptvApi> { IptvApi(get()) }
}