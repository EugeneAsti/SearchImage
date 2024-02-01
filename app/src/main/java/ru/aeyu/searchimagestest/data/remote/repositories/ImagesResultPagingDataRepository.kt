package ru.aeyu.searchimagestest.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aeyu.searchimagestest.data.remote.data_source.ImagesResultApi
import ru.aeyu.searchimagestest.data.remote.data_source.ImagesResultPagingSource
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.SearchQuery

class ImagesResultPagingDataRepository(
    private val imageResultApi: ImagesResultApi
) {
    fun getListImageItemDomainPagingDataFlow(searchQuery: SearchQuery): Flow<PagingData<ImageItem>> =
        Pager(PagingConfig(pageSize = 100)) {
            ImagesResultPagingSource(imageResultApi, searchQuery)
        }.flow
}