package ru.aeyu.searchimagestest.data.remote.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.SearchQuery

interface ImagesResultPagingDataRepository {
    fun getListImageItemPagingDataFlow(searchQuery: SearchQuery): Flow<PagingData<ImageItem>>
}