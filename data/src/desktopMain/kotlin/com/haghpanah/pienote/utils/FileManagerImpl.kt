package com.haghpanah.pienote.utils

import com.eygraber.uri.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileManagerImpl : FileManager {
    override suspend fun copyUriInCache(
        uri: Uri,
        targetFileName: String,
        targetDirName: String
    ): Uri? = withContext(Dispatchers.IO) {
        val sourceFile = uri.path
            ?.let { File(it) }
            ?: return@withContext null

        val targetFilesDir = File(cacheDir, targetDirName)
            .apply { if (!exists()) mkdirs() }
        val targetFile =
            File(
                targetFilesDir,
                targetFileName
            ).apply { if (!exists()) createNewFile() }


        sourceFile.copyTo(targetFile)

        return@withContext Uri.parseOrNull(targetFile.path)
    }

    companion object {
        val cacheDir = File(System.getProperty("java.io.tmpdir"))
    }
}