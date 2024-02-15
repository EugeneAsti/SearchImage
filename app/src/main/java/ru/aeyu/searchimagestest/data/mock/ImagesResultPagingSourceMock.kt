package ru.aeyu.searchimagestest.data.mock

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.aeyu.searchimagestest.data.remote.model.ImageItem

class ImagesResultPagingSourceMock(
    private val mockDataSource: MockDataSource,
) : PagingSource<Int, ImageItem>() {
    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {

        return try {
            var nextPageNumber = params.key ?: 0
            val assetsFileName = when (nextPageNumber) {
                0 -> "mock_search_result.json"
                1 -> ""
                else -> "mock_search_result.json"
            }
            val imagesList = mockDataSource(assetsFileName)
            val data = imagesList.searchResult ?: emptyList()
            nextPageNumber++
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}