package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.repository.CategoryRepository

class CategoryUpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryDomainModel: CategoryDomainModel) {
        categoryRepository.updateCategory(categoryDomainModel)
    }
}
