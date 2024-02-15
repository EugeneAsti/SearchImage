package ru.aeyu.searchimagestest.domain.models

import ru.aeyu.searchimagestest.domain.enums.ContentSizes
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.Languages

data class SearchFilter(
    val contentType: ContentTypes,
    val country: Countries,
    val contentSize: ContentSizes,
    val language: Languages
)

fun defaultSearchFilter(): SearchFilter{
    return SearchFilter(
        contentType = ContentTypes.IMAGES,
        country = Countries.ANY,
        contentSize = ContentSizes.IMAGE_ANY,
        language = Languages.ANY
    )
}