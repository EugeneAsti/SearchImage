package ru.aeyu.searchimagestest.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aeyu.searchimagestest.data.mock.ImagesResultPagingSourceMock
import ru.aeyu.searchimagestest.data.mock.MockDataSource
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.models.SearchQuery

class ImagesResultPagingDataRepositoryImplMock(
    private val mockDataSource: MockDataSource
) : ImagesResultPagingDataRepository {
    override fun getListImageItemPagingDataFlow(searchQuery: SearchQuery): Flow<PagingData<ImageItem>> =
        Pager(PagingConfig(pageSize = 100)) {
            ImagesResultPagingSourceMock(mockDataSource)
        }.flow
}