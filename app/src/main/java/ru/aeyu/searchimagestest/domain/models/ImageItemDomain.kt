package ru.aeyu.searchimagestest.domain.models

data class ImageItemDomain(
    val position: Int,
    val thumbnail: String,
    val relatedContentId: String,
    val serpapiRelatedContentLink: String,
    val source: String,
    val sourceLogo: String,
    val title: String,
    val link: String,
    val tag: String,
    val original: String,
    val originalWidth: Int,
    val originalHeight: Int,
    val isProduct: Boolean,
)