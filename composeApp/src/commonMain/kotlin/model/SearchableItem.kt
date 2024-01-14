package model

interface SearchableItem {
    val keyId: String
    fun doesMatchQuery(query: String): Boolean
}