package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuideItem(
    @SerialName("channel")
    val channel: String?,
    @SerialName("lang")
    val lang: String,
    @SerialName("site")
    val site: String,
    @SerialName("site_id")
    val siteId: String,
    @SerialName("site_name")
    val siteName: String
)