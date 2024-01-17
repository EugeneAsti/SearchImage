package ru.aeyu.searchimagestest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SettingsLocal")
data class SettingsLocal(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "Setting") val setting: String
)
