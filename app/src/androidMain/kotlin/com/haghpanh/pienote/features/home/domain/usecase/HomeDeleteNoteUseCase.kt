package pienote.features.home.domain.usecase

import pienote.commondomain.model.NoteDomainModel
import pienote.features.home.domain.repository.HomeRepository

class HomeDeleteNoteUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}
