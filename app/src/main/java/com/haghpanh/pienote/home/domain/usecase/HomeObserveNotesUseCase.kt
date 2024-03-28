package com.haghpanh.pienote.home.domain.usecase

import com.haghpanh.pienote.home.domain.model.NoteDomainModel
import com.haghpanh.pienote.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeObserveNotesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() : Flow<List<NoteDomainModel>> =
        homeRepository.observeNotes()
}
