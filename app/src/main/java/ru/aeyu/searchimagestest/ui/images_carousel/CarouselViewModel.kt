package ru.aeyu.searchimagestest.ui.images_carousel

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.domain.use_cases.SaveContentUseCase
import ru.aeyu.searchimagestest.ui.base.BaseViewModel


class CarouselViewModel : BaseViewModel<CarouselState, CarouselEffects>(CarouselState()) {

    override val classTag: String = "CarouselViewModel"


    override fun processCoroutineErrors(throwable: Throwable) {
        throwable.printStackTrace()
    }

    fun onFragmentCreated(
        imagesUrls: ArrayList<ContentItemDomain>?,
        currentImageUrl: String,
        contentTypeCode: String
    ) {
        viewModelScope.launch {
            val imagesList: ArrayList<ContentItemDomain> = arrayListOf()
            imagesUrls?.forEach {
                imagesList.add(it)
            }

            val pos = if (currentState.currentImagePos < 0) {
                val foundItem = imagesList.find { it.original == currentImageUrl }
                if (foundItem != null)
                    imagesList.indexOf(foundItem)
                else
                    0
            } else
                currentState.currentImagePos
            val contentType: ContentTypes = ContentTypes.getTypeByCode(contentTypeCode)
            fragmentState.emit(
                currentState.copy(
                    imagesList = imagesList.toList(),
                    isShowMessageText = false,
                    currentImagePos = pos,
                    contentType = contentType
                )
            )
        }
    }

    fun saveCurrentPosition(positionToSave: Int) {
        fragmentState.value = currentState.copy(currentImagePos = positionToSave)
    }

    override fun onCleared() {
        printLog("onCleared")
        super.onCleared()
    }

    fun onSharedButtonClicked(context: Context, currentPos: Int) {
        val imagesList = currentState.imagesList
        if (currentPos > imagesList.size - 1) {
            viewModelScope.launch {
                fragmentEffects.send(CarouselEffects.OnToastMessage("Невозможно поделиться содержимым :("))
            }
            return
        }
        val item = imagesList[currentPos]
        val fileName = StringBuilder().append(item.position).append(".jpg")

        val saveContent = SaveContentUseCase(
            contentUrl = item.original,
            contentType = currentState.contentType,
            context = context
        )
        viewModelScope.launch(ioContext) {

            fragmentState.emit(
                currentState.copy(
                    isLoading = true,
                    currentImagePos = currentPos
                )
            )
            val fileUri = saveContent.saveFileToDevice(fileName.toString())
            fragmentState.emit(currentState.copy(isLoading = false))
            if (fileUri != null)
                fragmentEffects.send(
                    CarouselEffects.OnShareResource(
                        currentState.contentType,
                        fileUri,
                        item.thumbnail.toUri()
                    )
                )
            else
                fragmentEffects.send(CarouselEffects.OnToastMessage("Не удалось прочитать файл"))

        }

    }
}