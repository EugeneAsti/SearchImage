package ru.aeyu.searchimagestest.data.local.source

import androidx.room.*
import ru.aeyu.searchimagestest.data.local.models.SettingsLocal

@Dao
interface ImagesDao {

    @Query("SELECT * FROM SettingsLocal")
    suspend fun getSettings() : List<SettingsLocal>
}
