package com.haghpanh.pienote.commondomain.usecase

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.haghpanh.pienote.features.note.utils.IMAGE_COVER_DIR_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class SaveImageUriInCacheUseCase(
    private val context: Context
) {
    // TODO Check for permission in lower apis
    suspend operator fun invoke(uri: Uri?): Uri? = withContext(Dispatchers.IO) {
        if (uri == null) return@withContext null

        val fileName = "Cover-${System.currentTimeMillis()}"

        val sourceFile = context.contentResolver.openInputStream(uri) ?: return@withContext null
        val targetFilesDir =
            File(context.cacheDir, IMAGE_COVER_DIR_NAME).apply { if (!exists()) mkdirs() }
        val targetFile =
            File(
                targetFilesDir,
                fileName
            ).apply { if (!exists()) createNewFile() }

        targetFile.outputStream().use { target ->
            sourceFile.copyTo(target)
        }
        sourceFile.close()

        return@withContext targetFile.toUri()
    }
}
