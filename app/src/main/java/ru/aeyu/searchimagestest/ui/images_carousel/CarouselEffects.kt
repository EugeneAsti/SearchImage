package ru.aeyu.searchimagestest.ui.images_carousel

import android.net.Uri
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.ui.base.FragmentListenableEffects

sealed class CarouselEffects : FragmentListenableEffects {
    data class OnAlertMessage(val message: String, val throwable: Throwable? = null) : CarouselEffects()
    data class OnToastMessage(val message: String) : CarouselEffects()
    data class OnShareResource(val contentType: ContentTypes, val contentUri: Uri, val contentThumbnailUri: Uri) : CarouselEffects()
}