package ru.aeyu.searchimagestest.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.aeyu.searchimagestest.data.remote.repositories.ImagesResultPagingDataRepository
import ru.aeyu.searchimagestest.domain.use_cases.GetImagesResultPagingUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainUseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideGetImagesResultPagingUseCase(
        remoteRepository: ImagesResultPagingDataRepository): GetImagesResultPagingUseCase =
        GetImagesResultPagingUseCase(remoteRepository)


}