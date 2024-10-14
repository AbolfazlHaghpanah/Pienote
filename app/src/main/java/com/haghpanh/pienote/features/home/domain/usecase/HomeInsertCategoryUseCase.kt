package com.haghpanh.pienote.features.home.domain.usecase

import android.net.Uri
import com.haghpanh.pienote.commondomain.repository.CommonRepository
import com.haghpanh.pienote.commondomain.usecase.SaveImageUriInCacheUseCase

class HomeInsertCategoryUseCase(
    private val commonRepository: CommonRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(name: String, image: Uri?) {
        val imageUri = saveImageUriInCacheUseCase(image)?.toString()

        commonRepository.insertCategory(
            name = name,
            image = imageUri
        )
    }
}
