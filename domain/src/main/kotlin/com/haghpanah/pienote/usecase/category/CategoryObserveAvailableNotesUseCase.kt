package com.haghpanah.pienote.usecase.category

import com.haghpanah.pienote.repository.CategoryRepository

class CategoryObserveAvailableNotesUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke() = categoryRepository.observeAvailableNotes()
}