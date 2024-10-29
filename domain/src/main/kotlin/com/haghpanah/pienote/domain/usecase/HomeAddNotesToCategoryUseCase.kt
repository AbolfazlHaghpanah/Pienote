package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.repository.CommonRepository

class HomeAddNotesToCategoryUseCase(
    private val commonRepository: CommonRepository
) {
    suspend operator fun invoke(noteIds: List<Int>, categoryId: Int) {
        commonRepository.addNotesToCategory(
            noteIds = noteIds,
            categoryId = categoryId
        )
    }
}
