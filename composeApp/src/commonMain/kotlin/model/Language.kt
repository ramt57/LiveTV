package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String
)