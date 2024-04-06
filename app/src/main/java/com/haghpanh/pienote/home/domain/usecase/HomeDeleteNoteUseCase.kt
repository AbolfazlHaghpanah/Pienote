package com.haghpanh.pienote.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeDeleteNoteUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}