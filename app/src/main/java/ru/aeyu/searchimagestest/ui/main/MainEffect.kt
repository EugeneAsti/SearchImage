package ru.aeyu.searchimagestest.ui.main

import ru.aeyu.searchimagestest.domain.models.ImageItemDomain
import ru.aeyu.searchimagestest.ui.base.FragmentListenableEffects

sealed class MainEffect : FragmentListenableEffects {
    data class OnImageClicked(val images: List<String>, val item: ImageItemDomain) : MainEffect()
    data class OnShareClicked(val item: ImageItemDomain) : MainEffect()
    data class OnWebClicked(val item: ImageItemDomain) : MainEffect()
    data class OnAlertMessage(val message: String, val throwable: Throwable? = null) : MainEffect()
}
