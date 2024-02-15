package ru.aeyu.searchimagestest.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMetadata(
    @SerialName("created_at")
    val createdAt: String? = "",
    @SerialName("google_images_url")
    val googleImagesUrl: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("json_endpoint")
    val jsonEndpoint: String? = "",
    @SerialName("prettify_html_file")
    val prettifyHtmlFile: String? = "",
    @SerialName("processed_at")
    val processedAt: String? = "",
    @SerialName("raw_html_file")
    val rawHtmlFile: String? = "",
    @SerialName("status")
    val status: String? = "",
    @SerialName("total_time_taken")
    val totalTimeTaken: Double? = 0.0
)