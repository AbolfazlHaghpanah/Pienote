package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository

class HomeDeleteNoteUseCase (
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}
