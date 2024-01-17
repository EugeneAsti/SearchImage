package ru.aeyu.searchimagestest.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeyu.searchimagestest.data.local.MainDataBase
import ru.aeyu.searchimagestest.data.local.source.ImagesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideOutletDao(
        db: MainDataBase
    ) : ImagesDao {
        return db.imagesDao()
    }

    @Provides
    @Singleton
    fun provideMainDataBase(
        @ApplicationContext context: Context
    ): MainDataBase {
       return Room.databaseBuilder(
           context,
           MainDataBase::class.java,
           MainDataBase.DB_NAME
       ).fallbackToDestructiveMigration()
           // отключает режим WAL
           .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
           .build()
    }
}