package ru.aeyu.searchimagestest.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchParameters(
    @SerialName("device")
    val device: String? = "",
    @SerialName("engine")
    val engine: String? = "",
    @SerialName("gl")
    val gl: String? = "",
    @SerialName("google_domain")
    val googleDomain: String? = "",
    @SerialName("hl")
    val hl: String? = "",
    @SerialName("ijn")
    val ijn: String? = "",
    @SerialName("q")
    val q: String? = ""
)