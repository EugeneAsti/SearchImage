package ru.aeyu.searchimagestest.data.remote.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.aeyu.searchimagestest.data.remote.model.ImageItem
import ru.aeyu.searchimagestest.domain.enums.ContentSizes
import ru.aeyu.searchimagestest.domain.models.SearchQuery

class ContentResultPagingSource(
    private val contentResultApi: ContentResultApi,
    private val searchQuery: SearchQuery
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
            val contentSize: String? =
                if (searchQuery.searchFilter.contentSize == ContentSizes.IMAGE_ANY
                    || searchQuery.searchFilter.contentSize == ContentSizes.VIDEO_ANY
                )
                    null
                else
                    searchQuery.searchFilter.contentSize.size

            val imagesList = contentResultApi.findImages(
                searchText = searchQuery.searchText,
                pageNumber = nextPageNumber,
                language = searchQuery.searchFilter.language.code,
                country = searchQuery.searchFilter.country.code,
                contentType = searchQuery.searchFilter.contentType.code,
                contentSize = contentSize
            )
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