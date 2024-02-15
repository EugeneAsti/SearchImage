package ru.aeyu.searchimagestest.ui.images_carousel

import ru.aeyu.searchimagestest.ui.base.FragmentListenableState

data class CarouselState(
    val isShowMessageText: Boolean = false,
    val messageText: String = "",
    val imagesList: List<String> = emptyList(),
    val currentImagePos: Int = -1
): FragmentListenableState
