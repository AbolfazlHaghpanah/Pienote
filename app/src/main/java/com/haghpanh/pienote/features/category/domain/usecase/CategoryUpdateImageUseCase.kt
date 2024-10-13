package com.haghpanh.pienote.features.category.domain.usecase

import android.net.Uri
import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.commondomain.usecase.SaveImageUriInCacheUseCase
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository

class CategoryUpdateImageUseCase (
    private val categoryRepository: CategoryRepository,
    private val saveImageUriInCacheUseCase: SaveImageUriInCacheUseCase
) {
    suspend operator fun invoke(currentCategory: CategoryDomainModel, uri: Uri?) {
        if (uri != null) {
            val savedUri = saveImageUriInCacheUseCase(uri)
            val newCategory = currentCategory.copy(image = savedUri.toString())

            categoryRepository.updateCategory(newCategory)
        }
    }
}
