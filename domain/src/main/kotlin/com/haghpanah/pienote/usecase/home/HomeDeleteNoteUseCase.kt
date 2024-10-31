package com.haghpanah.pienote.usecase.home

import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.repository.HomeRepository

class HomeDeleteNoteUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}