package ru.aeyu.searchimagestest.ui.images_carousel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.aeyu.searchimagestest.ui.base.BaseViewModel

class CarouselViewModel : BaseViewModel<CarouselState, CarouselEffects>(CarouselState()) {
    override val classTag: String = "CarouselViewModel"

    override fun processCoroutineErrors(throwable: Throwable) {
        throwable.printStackTrace()
    }

    fun generateImagesList(imagesUrls: Array<String>?){
        viewModelScope.launch {
            val imagesList: ArrayList<String> = arrayListOf()
            imagesUrls?.forEach {
                imagesList.add(it)
            }
            fragmentState.emit(currentState.copy(imagesList = imagesList, isShowMessageText = false))
        }
    }
}