package ru.aeyu.searchimagestest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.aeyu.searchimagestest.data.local.models.DownloadedImages
import ru.aeyu.searchimagestest.data.local.source.ImagesDao

@Database(
    entities = [DownloadedImages::class],//[BreedLocal::class, CatLocal::class],
    version = 1, exportSchema = false
)
//@TypeConverters(DateConverter::class)
abstract class MainDataBase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao

    companion object {
        const val DB_NAME = "images.db"
    }
}
