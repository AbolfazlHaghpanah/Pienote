package com.haghpanah.pienote.usecase.common

import com.eygraber.uri.Uri
import com.haghpanah.pienote.utils.FileManager

class SaveImageUriInCacheUseCase(
    private val fileManager: FileManager
) {
    suspend operator fun invoke(uri: Uri?): Uri? {
        if (uri == null) return null

        val fileName = "Cover-${System.currentTimeMillis()}"

        return fileManager.copyUriInCache(
            uri,
            targetFileName = fileName,
            targetDirName = IMAGE_COVER_DIR_NAME,
        )
    }

    companion object {
        const val IMAGE_COVER_DIR_NAME = "image_cover"
    }
}
