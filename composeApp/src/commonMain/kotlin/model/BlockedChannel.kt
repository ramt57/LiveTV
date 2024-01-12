package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockedChannel(
    @SerialName("channel")
    val channel: String,
    @SerialName("ref")
    val ref: String
)