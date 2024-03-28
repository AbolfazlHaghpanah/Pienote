package com.haghpanh.pienote.home.domain.usecase

import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeInsertNoteUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.insertNote(note)
    }
}