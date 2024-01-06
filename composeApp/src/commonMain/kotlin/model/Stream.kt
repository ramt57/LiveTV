package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stream(
    @SerialName("channel")
    val channel: String,
    @SerialName("url")
    val url: String,
    val timeshift: Int?,
    val http_referrer: String?,
    val user_agent: String?
)