package com.haghpanah.pienote.usecase.category

import com.eygraber.uri.Uri
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.repository.CategoryRepository
import com.haghpanah.pienote.usecase.common.SaveImageUriInCacheUseCase

class CategoryUpdateImageUseCase(
    private val categoryRepository: CategoryRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(currentCategory: CategoryDomainModel, uri: Uri?) {
        if (uri != null) {
            val savedUri = saveImageUriInCacheUseCase(uri)
            val newCategory = currentCategory.copy(
                image = requireNotNull(savedUri) {
                    "Fail To Save Image"
                }.toString()
            )

            categoryRepository.updateCategory(newCategory)
        }
    }
}
