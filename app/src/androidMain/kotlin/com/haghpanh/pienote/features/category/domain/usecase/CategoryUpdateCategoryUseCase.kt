package pienote.features.category.domain.usecase

import pienote.commondomain.model.CategoryDomainModel
import pienote.features.category.domain.repository.CategoryRepository

class CategoryUpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryDomainModel: CategoryDomainModel) {
        categoryRepository.updateCategory(categoryDomainModel)
    }
}
