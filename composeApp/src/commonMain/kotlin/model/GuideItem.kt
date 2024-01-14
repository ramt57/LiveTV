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
): SearchableItem {
    override val keyId: String
        get() = channel+ site

    override fun doesMatchQuery(query: String): Boolean {
        return false
    }
}