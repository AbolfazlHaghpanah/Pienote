package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.repository.CommonRepository


class HomeInsertCategoryUseCase(
    private val commonRepository: CommonRepository,
//    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(name: String, image: String?) {
        val imageUri = ""//saveImageUriInCacheUseCase(image)?.toString()

        commonRepository.insertCategory(
            name = name,
            image = imageUri
        )
    }
}