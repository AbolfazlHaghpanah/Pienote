package com.haghpanh.pienote.feature_home.domain.usecase

import com.haghpanh.pienote.feature_home.domain.model.QuickNoteDomainModel
import com.haghpanh.pienote.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeInsertQuickNoteUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: QuickNoteDomainModel) {
        homeRepository.insertNote(note)
    }
}