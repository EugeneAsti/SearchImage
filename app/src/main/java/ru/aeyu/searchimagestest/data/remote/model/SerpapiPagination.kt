package ru.aeyu.searchimagestest.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerpapiPagination(
    @SerialName("current")
    val current: Int? = 0,
    @SerialName("next")
    val next: String? = "",
    @SerialName("previous")
    val previous: String? = ""
)