package com.haghpanah.pienote.utils

import com.eygraber.uri.Uri

interface FileManager {
    suspend fun copyUriInCache(uri: Uri, targetFileName: String, targetDirName: String): Uri?
}
