package com.haghpanh.pienote.features.category.domain.usecase

import com.haghpanh.pienote.features.category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryDeleteNoteFromCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Int) {
        categoryRepository.deleteNoteFromCategory(noteId)
    }
}
