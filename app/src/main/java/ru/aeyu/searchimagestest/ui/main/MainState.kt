package ru.aeyu.searchimagestest.ui.main

import ru.aeyu.searchimagestest.data.remote.model.ImagesResult
import ru.aeyu.searchimagestest.domain.models.SearchFilter
import ru.aeyu.searchimagestest.domain.models.defaultSearchFilter
import ru.aeyu.searchimagestest.ui.base.FragmentListenableState

data class MainState(
    val isLoading: Boolean = false,
    val listImages: ImagesResult = ImagesResult(emptyList()),
    val imagesAdapter: MainAdapter? = null,
    val searchText: String = "",
    val filters: SearchFilter = defaultSearchFilter(),
    val isVisibleMessageText: Boolean = true,
    val textMessage: String = "Начните поиск, чтобы здесь что-то появилось :)"
): FragmentListenableState
