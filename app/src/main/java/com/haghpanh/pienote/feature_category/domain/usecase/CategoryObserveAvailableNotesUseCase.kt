package com.haghpanh.pienote.feature_category.domain.usecase

import com.haghpanh.pienote.feature_category.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryObserveAvailableNotesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke() = categoryRepository.observeAvailableNotes()
}