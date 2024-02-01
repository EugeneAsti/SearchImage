package ru.aeyu.searchimagestest.ui.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.ui.base.BaseViewModel
import ru.aeyu.searchimagestest.ui.main.ClickedElement.ITEM
import ru.aeyu.searchimagestest.ui.main.ClickedElement.SHARE
import ru.aeyu.searchimagestest.ui.main.ClickedElement.WEB
import ru.aeyu.searchimagestest.ui.utils.ImageDiffUtils

class MainViewModel : BaseViewModel<MainState, MainEffect>() {

    override val classTag: String = "MainViewModel"

    private var searchJob: Job? = null
    override fun processCoroutineErrors(throwable: Throwable) {
        printLog("coroutine err")
        throwable.printStackTrace()
    }

    override val initialState: MainState = MainState()

    override fun onFragmentStarted() {
        viewModelScope.launch(ioContext) {
            getStartedState()
        }
    }

    private suspend fun getStartedState() {
        val adapter = MainAdapter(ImageDiffUtils(), onItemClick)
        fragmentState.emit(currentState.copy(imagesAdapter = adapter))
    }

    fun onSearchImages(searchText: String) {
        runBlocking {
            searchJob?.cancelAndJoin()
        }
        searchJob = viewModelScope.launch(mainContext) {
            if (searchText.isEmpty())
                fragmentState.emit(
                    currentState.copy(
                        isLoading = false,
                        isVisibleMessageText = true,
                        textMessage = "Вы ничего не указали в строке поиска."
                    )
                )
            else {

            }
        }
    }

    private val onItemClick: (clickedElement: ClickedElement, imageItem: ImageItem) -> Unit =
        { clickedElement, imageItem ->
            viewModelScope.launch(mainContext) {
                when (clickedElement) {
                    ITEM -> {
                        fragmentEffects.send(MainEffect.OnImageClicked(imageItem))
                    }

                    SHARE -> {
                        fragmentEffects.send(MainEffect.OnShareClicked(imageItem))
                    }

                    WEB -> {
                        fragmentEffects.send(MainEffect.OnWebClicked(imageItem))
                    }
                }
            }
        }


    override fun onCleared() {
        viewModelScope.launch {
            searchJob?.cancelAndJoin()
        }
        super.onCleared()
    }
}