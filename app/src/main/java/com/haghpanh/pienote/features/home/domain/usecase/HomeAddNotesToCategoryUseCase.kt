package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.repository.CommonRepository

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
