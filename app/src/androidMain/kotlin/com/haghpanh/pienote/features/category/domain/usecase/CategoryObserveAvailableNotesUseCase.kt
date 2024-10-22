package pienote.features.category.domain.usecase

import pienote.features.category.domain.repository.CategoryRepository

class CategoryObserveAvailableNotesUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke() = categoryRepository.observeAvailableNotes()
}
