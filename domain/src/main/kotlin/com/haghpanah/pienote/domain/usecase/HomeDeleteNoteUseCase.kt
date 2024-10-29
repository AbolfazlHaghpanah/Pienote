package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.model.NoteDomainModel
import com.haghpanah.pienote.domain.repository.HomeRepository

class HomeDeleteNoteUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}