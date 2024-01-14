package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String
): SearchableItem {
    override val keyId: String
        get() = code

    override fun doesMatchQuery(query: String): Boolean {
        return false
    }
}