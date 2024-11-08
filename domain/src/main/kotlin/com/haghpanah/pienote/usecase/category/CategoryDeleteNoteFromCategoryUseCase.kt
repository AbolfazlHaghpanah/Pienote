package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.repository.CategoryRepository

class CategoryDeleteNoteFromCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: List<Long>) {
        categoryRepository.deleteNoteFromCategory(noteId)
    }
}