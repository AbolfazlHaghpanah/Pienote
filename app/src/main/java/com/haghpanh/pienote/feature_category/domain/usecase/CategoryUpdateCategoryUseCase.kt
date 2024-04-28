package com.haghpanh.pienote.feature_category.domain.usecase

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryUpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryDomainModel: CategoryDomainModel) {
        categoryRepository.updateCategory(categoryDomainModel)
    }
}