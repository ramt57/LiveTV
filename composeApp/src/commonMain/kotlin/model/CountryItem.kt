package model

import kotlinx.serialization.Serializable

@Serializable
data class CountryItem(
    val code: String,
    val flag: String,
    val languages: List<String>,
    val name: String
) : SearchableItem {
    override val keyId: String
        get() = code + name

    override fun doesMatchQuery(query: String): Boolean {
        val matchCombination = listOfNotNull(name, code, languages.joinToString { "," })
        return matchCombination.any { it.contains(query, ignoreCase = true) }
    }
}