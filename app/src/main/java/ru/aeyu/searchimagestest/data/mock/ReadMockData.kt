package ru.aeyu.searchimagestest.data.mock

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader


class ReadMockData(
    private val context: Context
) {
    fun readJSONFromAssets(assetsFileName: String): String {
        try {
            val assetManager: AssetManager = context.assets
            val file = assetManager.open(assetsFileName)
            val bufferedReader = BufferedReader(InputStreamReader(file))
            val stringBuilder = StringBuilder()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    stringBuilder.append(it)
                }
            }
            bufferedReader.close()
            return stringBuilder.toString()
        } catch (e: Exception) {
            Log.e(
                "!!!###!!!",
                " Error reading JSON: $e.",
            )
            e.printStackTrace()
            return ""
        }
    }
}