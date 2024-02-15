package ru.aeyu.searchimagestest.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeyu.searchimagestest.data.mock.MockDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockImagesResultModule {

    @Provides
    @Singleton
    fun provideMockDataSource(
        @ApplicationContext appContext: Context
    ): MockDataSource {
        return MockDataSource(appContext)
    }

}
