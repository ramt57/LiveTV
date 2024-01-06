package model

import kotlinx.serialization.Serializable

@Serializable
data class CountryItem(
    val code: String,
    val flag: String,
    val languages: List<String>,
    val name: String
)