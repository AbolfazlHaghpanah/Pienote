package com.haghpanah.pienote.usecase.home

import com.eygraber.uri.Uri
import com.haghpanah.pienote.repository.CommonRepository
import com.haghpanah.pienote.usecase.common.SaveImageUriInCacheUseCase

class HomeInsertCategoryUseCase(
    private val commonRepository: CommonRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(name: String, image: Uri?) {
        val imageUri = if (image != null) {
            requireNotNull(saveImageUriInCacheUseCase(image)).toString()
        } else {
            null
        }

        commonRepository.insertCategory(
            name = name,
            image = imageUri
        )
    }
}
