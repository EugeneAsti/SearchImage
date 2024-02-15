package ru.aeyu.searchimagestest.ui.images_carousel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.aeyu.searchimagestest.ui.base.BaseViewModel

class CarouselViewModel : BaseViewModel<CarouselState, CarouselEffects>(CarouselState()) {

    override val classTag: String = "CarouselViewModel"


    override fun processCoroutineErrors(throwable: Throwable) {
        throwable.printStackTrace()
    }

    fun onFragmentCreated(imagesUrls: Array<String>?, currentImageUrl: String) {
        viewModelScope.launch {
            val imagesList: ArrayList<String> = arrayListOf()
            imagesUrls?.forEach {
                imagesList.add(it)
            }

            val pos = if (currentState.currentImagePos < 0)
                imagesList.indexOf(currentImageUrl)
            else
                currentState.currentImagePos
            fragmentState.emit(
                currentState.copy(
                    imagesList = imagesList,
                    isShowMessageText = false,
                    currentImagePos = pos
                )
            )
        }
    }

    fun saveCurrentPosition(positionToSave: Int) {
        printLog("Previous state position: ${currentState.currentImagePos}")
        fragmentState.value = currentState.copy(currentImagePos = positionToSave)
        printLog("Current state position: ${fragmentState.value.currentImagePos}")
    }

    override fun onCleared() {
        printLog("onCleared")
        super.onCleared()
    }
}