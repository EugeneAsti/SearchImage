package ru.aeyu.searchimagestest.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.searchimagestest.BuildConfig
import ru.aeyu.searchimagestest.data.mock.MockDataSource
import ru.aeyu.searchimagestest.data.remote.data_source.ContentResultApi
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepository
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepositoryImpl
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepositoryImplMock

@Module
@InstallIn(SingletonComponent::class)
object RemoteImagesResultModule {

    @Provides
    fun provideImagesResultApi(
        retrofit: Retrofit
    ): ContentResultApi {
        return retrofit.create(ContentResultApi::class.java)
    }

    @Provides
    fun provideSearchResultPagingDataRepository(
        contentResultApi: ContentResultApi,
        mockDataSource: MockDataSource
    ): ImagesResultPagingDataRepository{
        return if(BuildConfig.USE_MOCK_DATA)
            ImagesResultPagingDataRepositoryImplMock(mockDataSource = mockDataSource)
        else
            ImagesResultPagingDataRepositoryImpl(contentResultApi)
    }

}
