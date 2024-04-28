package com.haghpanh.pienote.feature_category.domain.usecase

import com.haghpanh.pienote.common_domain.model.CategoryDomainModel
import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryDeleteNoteFromCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(noteId: Int) {
        categoryRepository.deleteNoteFromCategory(noteId)
    }
}