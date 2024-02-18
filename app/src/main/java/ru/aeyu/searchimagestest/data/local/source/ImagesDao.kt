package ru.aeyu.searchimagestest.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.aeyu.searchimagestest.data.local.models.DownloadedImages

// Это всё, пока, не используется
@Dao
interface ImagesDao {

    @Query("SELECT * FROM DownloadedImages")
    suspend fun getAllDownloadedImages(): List<DownloadedImages>

    @Query("SELECT * FROM DownloadedImages WHERE Image_Uri = :imageUri")
    suspend fun getDownloadedImageByUri(imageUri: String): List<DownloadedImages>

    @Insert
    suspend fun saveImageUri(newImage: DownloadedImages)
}
