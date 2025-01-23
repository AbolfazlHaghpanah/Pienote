package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.repository.CategoryRepository

class CategoryAddNoteToCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Long, categoryId: Long) {
        return categoryRepository.addNoteToCategory(noteId, categoryId)
    }
}
