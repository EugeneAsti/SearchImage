package ru.aeyu.searchimagestest.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepository
import ru.aeyu.searchimagestest.domain.mappers.toDomainModel
import ru.aeyu.searchimagestest.domain.models.ImageItemDomain
import ru.aeyu.searchimagestest.domain.models.SearchQuery

class GetImagesResultPagingUseCase(
    private val imagesResultPagingDataRepository: ImagesResultPagingDataRepository
) {
    operator fun invoke(searchQuery: SearchQuery): Flow<Result<PagingData<ImageItemDomain>>> =
        imagesResultPagingDataRepository.getListImageItemDomainPagingDataFlow(searchQuery)
            .catch {
                Result.failure<PagingData<ImageItemDomain>>(it)
            }.map { pagingData ->
                pagingData.map { apiItem ->
                    apiItem.toDomainModel()
                }
            }.map {
                Result.success(it)
            }

}