package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    val logo: String?,
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
    val website: String?,
    @Transient
    val url: String = ""
) : SearchableItem {
    override val keyId: String
        get() = id

    override fun doesMatchQuery(query: String): Boolean {
        val matchCombination = listOfNotNull(
            name,
            network,
            country,
            categories.joinToString { "," },
            languages.joinToString { "," },
            altNames?.joinToString { "," }
        )

        return matchCombination.any { it.contains(query, ignoreCase = true) }
    }
}

val channelList = listOf<Channel>()