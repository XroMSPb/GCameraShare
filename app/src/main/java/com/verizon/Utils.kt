package com.verizon

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

fun saveImageToPrivateStorage(bitmap: Bitmap, context: Context): Boolean {
    val filePath =
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "cache")
    if (!filePath.exists()) {
        filePath.mkdirs()
    }
    val file = File(filePath, "appIcon.webp")
    val outputStream = FileOutputStream(file)
    return bitmap
        .compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, outputStream)
}