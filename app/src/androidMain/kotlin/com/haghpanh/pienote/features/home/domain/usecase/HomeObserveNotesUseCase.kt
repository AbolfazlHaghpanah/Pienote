package pienote.features.home.domain.usecase

import pienote.commondomain.model.NoteDomainModel
import pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotes()
}
