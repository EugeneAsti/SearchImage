package ru.aeyu.searchimagestest.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aeyu.searchimagestest.data.remote.data_source.ContentResultApi
import ru.aeyu.searchimagestest.data.remote.data_source.ContentResultPagingSource
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.SearchQuery

class ImagesResultPagingDataRepositoryImpl(
    private val imageResultApi: ContentResultApi
): ImagesResultPagingDataRepository {
    override fun getListImageItemPagingDataFlow(searchQuery: SearchQuery): Flow<PagingData<ImageItem>> =
        Pager(PagingConfig(pageSize = 100)) {
            ContentResultPagingSource(imageResultApi, searchQuery)
        }.flow
}