package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.repository.CommonRepository
import com.sun.jndi.toolkit.url.Uri

class HomeInsertCategoryUseCase(
    private val commonRepository: CommonRepository,
//    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(name: String, image: Uri?) {
        val imageUri = ""//saveImageUriInCacheUseCase(image)?.toString()

        commonRepository.insertCategory(
            name = name,
            image = imageUri
        )
    }
}