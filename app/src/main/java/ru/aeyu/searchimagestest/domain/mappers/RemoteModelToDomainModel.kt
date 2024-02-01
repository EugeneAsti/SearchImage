package ru.aeyu.searchimagestest.domain.mappers

import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain

fun ImageItem.toDomainModel(): ImageItemDomain{
    return ImageItemDomain(
        position = position,
        thumbnail = thumbnail,
        relatedContentId = relatedContentId,
        serpapiRelatedContentLink = serpapiRelatedContentLink,
        source = source,
        sourceLogo = sourceLogo,
        title = title,
        link = link,
        tag = tag,
        original = original,
        originalWidth = originalWidth,
        originalHeight = originalHeight,
        isProduct = isProduct
    )
}