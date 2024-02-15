package ru.aeyu.searchimagestest.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultSerpApi(
    @SerialName("images_results")
    val searchResult: List<ImageItem>? = emptyList<ImageItem>(),
    @SerialName("search_information")
    val searchInformation: SearchInformation? = null,
    @SerialName("search_metadata")
    val searchMetadata: SearchMetadata? = null,
    @SerialName("search_parameters")
    val searchParameters: SearchParameters? = null,
    @SerialName("serpapi_pagination")
    val serpapiPagination: SerpapiPagination? = null
)