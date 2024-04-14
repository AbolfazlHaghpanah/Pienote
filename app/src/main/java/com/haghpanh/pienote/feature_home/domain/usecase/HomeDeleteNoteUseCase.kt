package com.haghpanh.pienote.feature_home.domain.usecase

import com.haghpanh.pienote.common_domain.model.NoteDomainModel
import com.haghpanh.pienote.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeDeleteNoteUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(note: NoteDomainModel) {
        homeRepository.deleteNote(note)
    }
}