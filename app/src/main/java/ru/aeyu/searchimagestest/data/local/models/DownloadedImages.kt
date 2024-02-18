package ru.aeyu.searchimagestest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DownloadedImages")
data class DownloadedImages(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Image_Uri") val imageUri: String,
    @ColumnInfo(name = "Image_Size") val size: Long
)
