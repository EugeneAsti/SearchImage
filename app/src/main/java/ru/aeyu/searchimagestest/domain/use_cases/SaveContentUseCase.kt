package ru.aeyu.searchimagestest.domain.use_cases

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import ru.aeyu.searchimagestest.domain.enums.ContentTypes
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL

class SaveContentUseCase(
    private val contentUrl: String,
    private val contentType: ContentTypes,
    private val context: Context
) {

    private val pathName = "SearchedImages"
    private val fullFileStorageName: String = Environment.DIRECTORY_PICTURES + File.separator +
            pathName

    fun saveFileToDevice(fileName: String): Uri? {
        return when (contentType) {
            ContentTypes.IMAGES -> {
                saveImage(fileName)
            }

            ContentTypes.VIDEOS -> {
                saveVideo(fileName)
            }
        }
    }

    private fun saveVideo(fileName: String): Uri? {
        return null
    }

    private fun saveImage(fileName: String): Uri? {
        val existingUri: Uri? = getExistingFile(fileName, context.contentResolver)
        if (existingUri != null)
            return existingUri

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            saveFileOnAndroidLess29(fileName)
        else
            saveFileOnAndroid29AndLater(fileName, context.contentResolver)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileOnAndroid29AndLater(
        fileName: String,
        contentResolver: ContentResolver
    ): Uri? {

        val newFileContentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, fullFileStorageName)
        }
        val uri =
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                newFileContentValues
            )
        val result = if (uri != null) {
            val inputStream = getContentUrlStream()
            inputStream?.use { input ->
                contentResolver.openOutputStream(uri).use { output ->
                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                }
            } ?: -1
        } else {
            -1
        }
        return if(result > 0)
            uri
        else
            null
    }

    private fun getExistingFile(fileName: String, contentResolver: ContentResolver): Uri? {

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )

        val selection = "${MediaStore.Images.Media.RELATIVE_PATH} = ? AND ${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fullFileStorageName + File.separator, fileName)

// Display videos in alphabetical order based on their display name.
//        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            null
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            if(cursor.count > 0) {
                cursor.moveToNext()
                val id = cursor.getLong(idColumn)
                return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

            }else
                return null
        } ?: return null
    }

    private fun getContentUrlStream(): InputStream? {
        return try {
            URL(contentUrl).openStream()
        } catch (ex: IOException) {
            Log.e(
                "!!!###!!!",
                "[SaveContentUseCase] getContentUrlStream: Can't load file!"
            )
            ex.printStackTrace()
            null
        }
    }

    private fun saveFileOnAndroidLess29(fileName: String): Uri? {
//        val inputStream =
//            try {
//                URL(contentUrl).openStream()
//            } catch (e: Exception) {
//                Log.e("!!!###!!!", "[SaveContentUseCase] saveFileOnAndroidLess29: Can't load file!")
//                e.printStackTrace()
//                null
//            }
//        val result = inputStream?.use { input ->
//            FileOutputStream(fileToSave).use { output ->
//                input.copyTo(output)
//            }
//        } ?: -1
//        return result
        return null
    }
}