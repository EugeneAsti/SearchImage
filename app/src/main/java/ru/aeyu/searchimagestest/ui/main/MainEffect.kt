package ru.aeyu.searchimagestest.ui.main

import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.ui.base.FragmentListenableEffects

sealed class MainEffect : FragmentListenableEffects {
    data class OnImageClicked(val item: ImageItem) : MainEffect()
    data class OnShareClicked(val item: ImageItem) : MainEffect()
    data class OnWebClicked(val item: ImageItem) : MainEffect()
    data class OnAlertMessage(val message: String, val throwable: Throwable? = null) : MainEffect()
}
