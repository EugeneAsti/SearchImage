package ru.aeyu.searchimagestest.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.searchimagestest.domain.enums.ContentSizes
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import ru.aeyu.searchimagestest.domain.enums.Countries
import ru.aeyu.searchimagestest.domain.enums.Languages
import ru.aeyu.searchimagestest.domain.enums.MenuContentSizes
import ru.aeyu.searchimagestest.domain.models.ContentItemDomain
import ru.aeyu.searchimagestest.domain.models.SearchFilter
import ru.aeyu.searchimagestest.domain.models.SearchQuery
import ru.aeyu.searchimagestest.domain.models.defaultSearchFilter
import ru.aeyu.searchimagestest.domain.use_cases.GetImagesResultPagingUseCase
import ru.aeyu.searchimagestest.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImagesResultPagingUseCase: GetImagesResultPagingUseCase
) : BaseViewModel<MainState, MainEffect>(MainState()) {

    override val classTag: String = "MainViewModel"

    private var searchJob: Job? = null

    var currentSearchFilter: SearchFilter = defaultSearchFilter()

    override fun processCoroutineErrors(throwable: Throwable) {
        printLog("coroutine err")
        throwable.printStackTrace()
    }

    fun onNewSearchQuery(query: String?) {
        viewModelScope.launch {
            searchJob?.cancelAndJoin()
        }
        if (query.isNullOrEmpty()) {
            viewModelScope.launch {
                fragmentState.emit(
                    currentState.copy(
                        isLoading = false,
                        isVisibleMessageText = true,
                        textMessage = "Вы ничего не указали в строке поиска. :)"
                    )
                )
            }
        } else
            onSearchImages(query)
    }

    private fun onSearchImages(searchText: String) {

        searchJob = viewModelScope.launch(mainContext) {
            fragmentState.emit(currentState.copy(searchText = searchText))
            if (searchText.isEmpty())
                fragmentState.emit(
                    currentState.copy(
                        isLoading = false,
                        isVisibleMessageText = true,
                        textMessage = "Вы ничего не указали в строке поиска."
                    )
                )
            else {
                val searchQuery = SearchQuery(
                    searchText = searchText,
                    currentSearchFilter
                )

                getImagesResultPagingUseCase(viewModelScope, searchQuery)
                    .flowOn(ioContext)
                    .onStart {
                        fragmentState.emit(currentState.copy(isLoading = true))
                    }.flowOn(mainContext).collect { result ->
                        result.onFailure {
                            fragmentState.emit(currentState.copy(isLoading = false))
                            fragmentEffects.send(
                                MainEffect.OnAlertMessage("Ошибка загрузки данных", it)
                            )
                        }.onSuccess { pagingData ->
                            printLog("CoroutineContext: ${this.coroutineContext}")
                            printLog("Has data: $pagingData")
                            fragmentState.emit(
                                currentState.copy(
                                    pagingData = pagingData,
                                    isLoading = false,
                                    isVisibleMessageText = false
                                )
                            )
                        }
                    }
            }
        }
    }

    fun onContentTypeChange(newContentType: ContentTypes) {
        currentSearchFilter = currentState.filters.copy(contentType = newContentType)
        viewModelScope.launch(mainContext) {
            fragmentState.emit(currentState.copy(filters = currentSearchFilter))
            printLog("ContentType = ${fragmentState.value.filters.contentType}")
        }
    }

    fun onContentSizeChange(menuContentSize: MenuContentSizes) {
        val newContentSize =
            ContentSizes.getSize(currentState.filters.contentType, menuContentSize)
        currentSearchFilter = currentState.filters.copy(contentSize = newContentSize)
        viewModelScope.launch(mainContext) {
            fragmentState.emit(currentState.copy(filters = currentSearchFilter))
            printLog("ContentSize = ${fragmentState.value.filters.contentSize}")
        }
    }

    fun onCountryResultChange(newCountry: Countries) {
        currentSearchFilter = currentState.filters.copy(country = newCountry)
        viewModelScope.launch(mainContext) {
            fragmentState.emit(currentState.copy(filters = currentSearchFilter))
        }
    }

    fun onLanguageResultChange(newLanguage: Languages) {
        currentSearchFilter = currentState.filters.copy(language = newLanguage)
        viewModelScope.launch(mainContext) {
            fragmentState.emit(currentState.copy(filters = currentSearchFilter))
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            searchJob?.cancelAndJoin()
        }
        super.onCleared()
    }

    fun onItemClicked(imagesList: List<ContentItemDomain>, imageItem: ContentItemDomain) {
        viewModelScope.launch {
            val arrList: ArrayList<ContentItemDomain> = java.util.ArrayList(imagesList)
            val contentTypeCode = currentState.filters.contentType.code
            fragmentEffects.send(MainEffect.OnImageClicked(arrList, imageItem, contentTypeCode))
        }
    }
}