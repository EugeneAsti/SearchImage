package ru.aeyu.searchimagestest.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.searchimagestest.data.remote.data_source.ImagesResultApi
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepository

@Module
@InstallIn(SingletonComponent::class)
object RemoteImagesResultModule {

    @Provides
    fun provideImagesResultApi(
        retrofit: Retrofit
    ): ImagesResultApi {
        return retrofit.create(ImagesResultApi::class.java)
    }

    @Provides
    fun provideSearchResultPagingDataRepository(imagesResultApi: ImagesResultApi): ImagesResultPagingDataRepository =
        ImagesResultPagingDataRepository(imagesResultApi)
}
