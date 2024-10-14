package com.haghpanh.pienote.features.home.domain.usecase

import com.haghpanh.pienote.commondomain.model.NoteDomainModel
import com.haghpanh.pienote.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotes()
}
