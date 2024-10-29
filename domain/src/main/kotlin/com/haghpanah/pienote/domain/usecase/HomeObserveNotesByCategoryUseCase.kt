package com.haghpanah.pienote.domain.usecase

import com.haghpanah.pienote.domain.model.NoteDomainModel
import com.haghpanah.pienote.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeObserveNotesByCategoryUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(categoryId: Int): Flow<List<NoteDomainModel>> =
        homeRepository.observeNotesByCategory(categoryId)
}