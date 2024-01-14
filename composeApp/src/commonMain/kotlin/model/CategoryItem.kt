package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItem(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
): SearchableItem {
    override val keyId: String
        get() = id

    override fun doesMatchQuery(query: String): Boolean {
        val matchCombination = listOfNotNull(name, id)
        return matchCombination.any { it.contains(query, ignoreCase = true) }
    }
}