package com.haghpanah.pienote.usecase.home

import com.eygraber.uri.Uri
import com.haghpanah.pienote.repository.CommonRepository
import com.haghpanah.pienote.usecase.common.SaveImageUriInCacheUseCase

class HomeInsertCategoryUseCase(
    private val commonRepository: CommonRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(name: String, image: Uri?) {
        val imageUri = requireNotNull(saveImageUriInCacheUseCase(image)).toString()

        commonRepository.insertCategory(
            name = name,
            image = imageUri
        )
    }
}
