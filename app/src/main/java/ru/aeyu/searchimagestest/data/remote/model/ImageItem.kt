package ru.aeyu.searchimagestest.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageItem(
    val position: Int,
    val thumbnail: String? = "",
    @SerialName("related_content_id")
    val relatedContentId: String? = "",
    @SerialName("serpapi_related_content_link")
    val serpapiRelatedContentLink: String? = "",
    val source: String? = "",
    @SerialName("source_logo")
    val sourceLogo: String? = "",//?
    val title: String? = "",
    val link: String? = "",
    val tag: String? = "",//?
    val original: String? = "",
    @SerialName("original_width")
    val originalWidth: Int? = 0,
    @SerialName("original_height")
    val originalHeight: Int? = 0,
    @SerialName("is_product")
    val isProduct: Boolean? = false, //?
)