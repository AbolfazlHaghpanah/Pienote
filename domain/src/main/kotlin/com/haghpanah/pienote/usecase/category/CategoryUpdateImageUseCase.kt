package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.repository.CategoryRepository

class CategoryUpdateImageUseCase(
    private val categoryRepository: CategoryRepository,
    //private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(currentCategory: CategoryDomainModel, uri: String?) {
        if (uri != null) {
            val savedUri = ""//TODO saveImageUriInCacheUseCase(uri)
            val newCategory = currentCategory.copy(image = savedUri.toString())

            categoryRepository.updateCategory(newCategory)
        }
    }
}
