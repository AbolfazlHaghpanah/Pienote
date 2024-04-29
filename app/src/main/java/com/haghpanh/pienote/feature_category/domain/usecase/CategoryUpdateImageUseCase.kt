package com.haghpanh.pienote.feature_category.domain.usecase

import android.net.Uri
import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.usecase.SaveImageUriInCacheUseCase
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryUpdateImageUseCase @Inject constructor(
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