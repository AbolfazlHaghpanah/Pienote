package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.repository.CommonRepository

class HomeAddNotesToCategoryUseCase(
    private val commonRepository: CommonRepository
) {
    suspend operator fun invoke(noteIds: List<Long>, categoryId: Long) {
        commonRepository.addNotesToCategory(
            noteIds = noteIds,
            categoryId = categoryId
        )
    }
}
