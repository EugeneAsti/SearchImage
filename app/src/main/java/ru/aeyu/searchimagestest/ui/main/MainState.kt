package ru.aeyu.searchimagestest.ui.main

import androidx.paging.PagingData
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain
import ru.aeyu.searchimagestest.domain.models.SearchFilter
import ru.aeyu.searchimagestest.domain.models.defaultSearchFilter
import ru.aeyu.searchimagestest.ui.base.FragmentListenableState

data class MainState(
    val isLoading: Boolean = false,
    val pagingData: PagingData<ImageItemDomain> = PagingData.empty(),
    val searchText: String = "",
    val filters: SearchFilter = defaultSearchFilter(),
    val isVisibleMessageText: Boolean = true,
    val textMessage: String = "Начните поиск, чтобы здесь что-то появилось :)"
): FragmentListenableState
