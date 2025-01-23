package com.haghpanah.pienote.utils

import android.content.Context
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileManagerImpl(
    private val context: Context
) : FileManager {

    override suspend fun copyUriInCache(
        uri: Uri,
        targetFileName: String,
        targetDirName: String
    ): Uri? = withContext(Dispatchers.IO) {
        val sourceFile = context.contentResolver.openInputStream(uri.toAndroidUri())
            ?: return@withContext null
        val targetFilesDir =
            File(context.cacheDir, targetDirName).apply { if (!exists()) mkdirs() }
        val targetFile =
            File(
                targetFilesDir,
                targetFileName
            ).apply { if (!exists()) createNewFile() }

        targetFile.outputStream().use { target ->
            sourceFile.copyTo(target)
        }
        sourceFile.close()

        return@withContext Uri.parseOrNull(targetFile.path)
    }
}