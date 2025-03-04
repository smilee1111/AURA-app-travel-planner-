package com.example.aura_app

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

fun getRealPathFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val fileName = it.getString(nameIndex)
        val file = File(context.cacheDir, fileName)
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
    return null
}