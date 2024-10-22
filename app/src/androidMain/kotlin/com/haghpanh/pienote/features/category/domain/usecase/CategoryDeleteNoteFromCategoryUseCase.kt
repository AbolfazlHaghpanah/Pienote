package pienote.features.category.domain.usecase

import pienote.features.category.domain.repository.CategoryRepository

class CategoryDeleteNoteFromCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Int) {
        categoryRepository.deleteNoteFromCategory(noteId)
    }
}
