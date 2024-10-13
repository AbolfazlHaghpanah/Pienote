package com.haghpanh.pienote.features.category.domain.usecase

import com.haghpanh.pienote.commondomain.model.CategoryDomainModel
import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository

class CategoryUpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryDomainModel: CategoryDomainModel) {
        categoryRepository.updateCategory(categoryDomainModel)
    }
}
