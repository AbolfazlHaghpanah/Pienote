package pienote.features.home.domain.usecase

import pienote.commondomain.repository.CommonRepository

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
