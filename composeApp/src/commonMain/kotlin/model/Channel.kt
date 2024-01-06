package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    @SerialName("alt_names")
    val altNames: List<String>?,
    @SerialName("broadcast_area")
    val broadcastArea: List<String>?,
    @SerialName("categories")
    val categories: List<String>,
    @SerialName("city")
    val city: String?,
    @SerialName("closed")
    val closed: String?,
    @SerialName("country")
    val country: String,
    @SerialName("id")
    val id: String,
    @SerialName("is_nsfw")
    val isNsfw: Boolean,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("launched")
    val launched: String?,
    @SerialName("logo")
    val logo: String,
    @SerialName("name")
    val name: String,
    @SerialName("network")
    val network: String?,
    @SerialName("owners")
    val owners: List<String>?,
    @SerialName("replaced_by")
    val replacedBy: String?,
    @SerialName("subdivision")
    val subdivision: String?,
    @SerialName("website")
    val website: String?
)

val channelList = listOf<Channel>()