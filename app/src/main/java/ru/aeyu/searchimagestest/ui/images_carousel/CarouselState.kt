package ru.aeyu.searchimagestest.ui.images_carousel

import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.ui.base.FragmentListenableState

data class CarouselState(
    val isLoading: Boolean = false,
    val isShowMessageText: Boolean = false,
    val messageText: String = "",
    val imagesList: List<ContentItemDomain> = emptyList(),
    val currentImagePos: Int = -1,
    val contentType: ContentTypes = ContentTypes.IMAGES
): FragmentListenableState
