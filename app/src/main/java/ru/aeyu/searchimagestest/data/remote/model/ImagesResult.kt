package ru.aeyu.searchimagestest.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResult(
    @SerialName("images_results")
    val searchResult: List<ImageItem>
)
