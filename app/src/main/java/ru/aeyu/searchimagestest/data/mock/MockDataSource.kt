package ru.aeyu.searchimagestest.data.mock

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.aeyu.searchimagestest.data.remote.model.SearchResultSerpApi

class MockDataSource(private val context: Context) {
    operator fun invoke(assetsFileName: String) : SearchResultSerpApi {
        val mockData = ReadMockData(context)
        val jsonString = mockData.readJSONFromAssets(assetsFileName)
        return Json.decodeFromString<SearchResultSerpApi>(jsonString)
    }
}