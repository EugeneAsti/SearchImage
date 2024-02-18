package ru.aeyu.searchimagestest.ui.main

import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.ui.base.FragmentListenableEffects
import java.util.ArrayList

sealed class MainEffect : FragmentListenableEffects {
    data class OnImageClicked(val images: ArrayList<ContentItemDomain>, val item: ContentItemDomain, val currentItemTypeCode: String) : MainEffect()
    data class OnShareClicked(val item: ContentItemDomain) : MainEffect()
    data class OnWebClicked(val item: ContentItemDomain) : MainEffect()
    data class OnAlertMessage(val message: String, val throwable: Throwable? = null) : MainEffect()
}
