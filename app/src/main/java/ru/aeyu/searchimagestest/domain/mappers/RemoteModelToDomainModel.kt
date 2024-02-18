package ru.aeyu.searchimagestest.domain.mappers

import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain

fun ImageItem.toDomainModel(): ContentItemDomain{
    return ContentItemDomain(
        position = position,
        thumbnail = thumbnail ?: "",
        relatedContentId = relatedContentId ?: "",
        serpapiRelatedContentLink = serpapiRelatedContentLink ?: "",
        source = source ?: "",
        sourceLogo = sourceLogo ?: "",
        title = title ?: "",
        link = link ?: "",
        tag = tag ?: "",
        original = original ?: "",
        originalWidth = originalWidth ?: 0,
        originalHeight = originalHeight ?: 0,
        isProduct = isProduct ?: false
    )
}