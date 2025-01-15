package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.repository.CommonRepository

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
