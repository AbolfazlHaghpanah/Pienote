package com.haghpanh.pienote.feature_category.domain.usecase

import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryAddNoteToCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Int, categoryId: Int) {
        return categoryRepository.addNoteToCategory(noteId, categoryId)
    }
}

