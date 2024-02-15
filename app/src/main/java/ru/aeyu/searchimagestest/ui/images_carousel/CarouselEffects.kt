package ru.aeyu.searchimagestest.ui.images_carousel

import ru.aeyu.searchimagestest.ui.base.FragmentListenableEffects

sealed class CarouselEffects : FragmentListenableEffects {
    data class OnAlertMessage(val message: String, val throwable: Throwable? = null) : CarouselEffects()
}