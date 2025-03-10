package com.hajong.blackboard.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object FileUtil {
    private const val MAX_WIDTH = 1280
    private const val MAX_HEIGHT = 960

    suspend fun optimizeAndResizeBitmap(
        context: Context,
        uriList: List<Uri>
    ): List<MultipartBody.Part>? {
        return try {
            withContext(Dispatchers.IO) {
                val fileList = mutableListOf<MultipartBody.Part>()
                uriList.mapNotNull { uri ->
                    val rotatedBitmap = rotateBitmapIfRequired(context, uri)
                    rotatedBitmap?.let { bitmap ->
                        val compressedPath = compressBitmap(context, bitmap, uri)
                        val filePart = addImageFileToRequestBody(compressedPath, "files")
                        fileList.add(filePart)
                    }
                }
                fileList.takeIf { it.isNotEmpty() }
            }
        } catch (e: Exception) {
            Log.e("FileUtil", "Error in optimizeAndResizeBitmap: ${e.message}")
            null
        }
    }

    private fun sampleBitmap(context: Context, uri: Uri): Bitmap? {
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)
            options.inSampleSize = calculateInSampleSize(options)
            options.inJustDecodeBounds = false

            return BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(uri),
                null,
                options
            )
        } catch (e: Exception) {
            Log.e("FileUtil", "Error in sampleBitmap: ${e.message}")
            return null
        }
    }

    private fun rotateBitmapIfRequired(context: Context, uri: Uri): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)
            val exif = ExifInterface(context.contentResolver.openInputStream(uri)!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap!!, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap!!, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap!!, 270)
                else -> bitmap
            }
        } catch (e: Exception) {
            Log.e("FileUtil", "Error in rotateBitmapIfRequired: ${e.message}")
            null
        }
    }

    private fun compressBitmap(context: Context, bitmap: Bitmap, uri: Uri): String {
        return try {
            val storageDir = context.cacheDir
            val fileName = "Android_${UUID.randomUUID()}.jpeg"
            val tempFile = File(storageDir, fileName)
            val fos = FileOutputStream(tempFile)
            val targetByte = 10 * 1024 * 1024

            var quality = 100
            var fileSize = 0L
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                fileSize = cursor.getLong(sizeIndex)
            }

            quality = if (fileSize.toInt() >= targetByte) {
                val divide = (targetByte.toFloat() / fileSize.toFloat())
                (divide * 100).toInt()
            } else {
                50
            }

            Log.e("FileUtil", "quality : $quality")
            Log.e("FileUtil", "size : $fileSize")

            // 압축률 계산 해서 적용
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            fos.flush()
            fos.close()
            tempFile.absolutePath
        } catch (e: Exception) {
            Log.e("FileUtil", "Error in compressBitmap: ${e.message}")
            ""
        }
    }

    private fun addImageFileToRequestBody(path: String, name: String): MultipartBody.Part {
        val imageFile = File(path)
        val fileRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, imageFile.name, fileRequestBody)
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix().apply { postRotate(degree.toFloat()) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_HEIGHT && halfWidth / inSampleSize >= MAX_WIDTH) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}