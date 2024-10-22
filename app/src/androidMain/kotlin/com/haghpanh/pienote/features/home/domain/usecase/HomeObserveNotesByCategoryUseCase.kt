package pienote.features.home.domain.usecase

import pienote.commondomain.model.NoteDomainModel
import pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesByCategoryUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotesByCategory(categoryId)
}
