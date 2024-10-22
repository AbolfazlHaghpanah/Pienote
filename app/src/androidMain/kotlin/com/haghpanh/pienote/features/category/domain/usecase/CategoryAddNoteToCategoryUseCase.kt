package pienote.features.category.domain.usecase

import pienote.features.category.domain.repository.CategoryRepository

class CategoryAddNoteToCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Int, categoryId: Int) {
        return categoryRepository.addNoteToCategory(noteId, categoryId)
    }
}
